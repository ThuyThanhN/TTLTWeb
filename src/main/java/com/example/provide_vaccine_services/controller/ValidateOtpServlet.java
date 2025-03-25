package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.OTPGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "ValidateOtpServlet", value = "/verify-reset-passwd")
public class ValidateOtpServlet extends HttpServlet {

    // Thời gian khóa sau khi nhập sai 3 lần OTP (1 phút)
    private static final long LOCK_DURATION = 60 * 1000; // 1 phút (60 giây)
    private static final long RESEND_INTERVAL = 0 * 1000; // Thời gian giữa các lần gửi mã OTP (1 phút)
    private static final int MAX_OTP_SENT_PER_DAY = 3; // Giới hạn số lần gửi mã OTP trong một ngày (3 lần)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến verify-reset-passwd.jsp nếu người dùng truy cập bằng GET
        request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu có yêu cầu gửi lại OTP
        String resendOtp = request.getParameter("resendOtp");
        if (resendOtp != null && resendOtp.equals("true")) {
            resendOtp(request, response);  // Gọi hàm xử lý gửi lại OTP
            return;  // Sau khi gửi OTP lại, dừng tiếp tục xử lý các logic còn lại
        }

        // Lấy mã OTP và email từ request và session
        String enteredOtp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");
        String email = (String) request.getSession().getAttribute("email");
        Integer failedAttempts = (Integer) request.getSession().getAttribute("failedAttempts");
        Long lockTime = (Long) request.getSession().getAttribute("lockTime");

        // Kiểm tra xem người dùng đã bị khóa chưa (lockTime có giá trị và chưa hết thời gian khóa)
        if (lockTime != null && new Date().getTime() - lockTime < LOCK_DURATION) {
            // Nếu người dùng vẫn bị khóa, tính thời gian còn lại và hiển thị thông báo
            long timeLeft = (LOCK_DURATION - (new Date().getTime() - lockTime)) / 1000;
            request.setAttribute("error", "Bạn đã nhập sai 3 lần. Vui lòng đợi " + timeLeft + " giây để thử lại.");
            request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
            return;
        }

        // Kiểm tra OTP
        if (sessionOtp != null && sessionOtp.equals(enteredOtp)) {
            // OTP hợp lệ, reset số lần nhập sai
            request.getSession().setAttribute("failedAttempts", 0);
            request.getSession().removeAttribute("lockTime"); // Xóa thời gian khóa nếu OTP đúng
            response.sendRedirect(request.getContextPath() + "/updatePasswd"); // Chuyển hướng đến trang update mật khẩu
        } else {
            // Nếu sai, tăng số lần nhập sai
            if (failedAttempts == null) {
                failedAttempts = 0;
            }
            failedAttempts++;
            request.getSession().setAttribute("failedAttempts", failedAttempts);

            // Nếu số lần nhập sai >= 3, lưu thời gian khóa
            if (failedAttempts >= 3) {
                request.getSession().setAttribute("lockTime", new Date().getTime()); // Lưu thời gian người dùng bị khóa
            }

            request.setAttribute("error", "Mã OTP không đúng! Bạn còn " + (3 - failedAttempts) + " lần thử.");
            request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
        }
    }

    // Xử lý gửi lại mã OTP (Gửi mã mới cho người dùng nếu đã bị khóa hoặc yêu cầu gửi lại)
    private void resendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer otpSentCount = (Integer) request.getSession().getAttribute("otpSentCount");
        Long lastOtpTime = (Long) request.getSession().getAttribute("lastOtpTime");

        // Kiểm tra nếu đã vượt quá số lần gửi mã OTP trong ngày
        if (otpSentCount != null && otpSentCount >= MAX_OTP_SENT_PER_DAY) {
            response.getWriter().write("Bạn đã gửi quá 3 lần mã OTP trong ngày. Vui lòng thử lại vào ngày mai.");
            return;
        }

        // Kiểm tra thời gian gửi mã trước đó
        if (lastOtpTime != null && new Date().getTime() - lastOtpTime < RESEND_INTERVAL) {
            response.getWriter().write("Vui lòng đợi ít nhất 1 phút để gửi mã OTP lại.");
            return;
        }

        String email = (String) request.getSession().getAttribute("email");

        if (email != null) {
            // Tạo và gửi mã OTP
            String newOtp = OTPGenerator.generateOTP();
            EmailSender.sendEmail(email, "Reset Password OTP", "Mã OTP mới của bạn là: " + newOtp);

            // Cập nhật lại OTP trong session
            request.getSession().setAttribute("otp", newOtp);
            request.getSession().setAttribute("otpCreatedTime", System.currentTimeMillis());
            request.getSession().setAttribute("lastOtpTime", new Date().getTime()); // Lưu thời gian gửi mã OTP
            // Tăng số lần gửi mã OTP
            if (otpSentCount == null) {
                otpSentCount = 0;
            }
            otpSentCount++;
            request.getSession().setAttribute("otpSentCount", otpSentCount); // Cập nhật số lần gửi OTP trong ngày

            response.getWriter().write("Mã OTP mới đã được gửi đến email của bạn.");
        } else {
            response.getWriter().write("Không tìm thấy email trong session.");
        }
    }
}

package com.example.provide_vaccine_services.controller;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến verify-reset-passwd.jsp nếu người dùng truy cập bằng GET
        request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}

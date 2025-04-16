package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.OTPGenerator;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ForgotPasswordServlet", value = "/reset-password")
public class ForgotPasswordServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu OTP đã được nhập và còn hiệu lực, không cho phép nhập lại
        if (request.getSession().getAttribute("otpEntered") != null) {
            // Nếu OTP đã được nhập, chuyển hướng đến trang khác hoặc thông báo
            request.setAttribute("error", "Mã OTP đã được nhập.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Chuyển hướng người dùng đến trang reset-password.jsp
        request.getRequestDispatcher("reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            // Kiểm tra xem email có tồn tại không
            if (userDao.isEmailExists(email)) {
                // Kiểm tra nếu OTP đã được gửi và không được nhập lại
                if (request.getSession().getAttribute("otpEntered") != null) {
                    request.setAttribute("error", "Mã OTP đã được nhập. Bạn không thể nhập lại.");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                // Tạo và gửi mã OTP nếu chưa nhập OTP trước đó
                String otp = OTPGenerator.generateOTP();
                EmailSender.sendEmail(email, "Reset Password OTP", "Mã OTP của bạn là: " + otp);

                // Lưu OTP vào session
                request.getSession().setAttribute("otp", otp);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("otpCreatedTime", System.currentTimeMillis());

                request.setAttribute("message", "Đang gửi mã OTP đến email của bạn.");
                response.sendRedirect(request.getContextPath() + "/verify-reset-passwd");
            } else {
                request.setAttribute("error", "Email không tồn tại trong hệ thống.");
                request.getRequestDispatcher("reset-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }
    }

    // Phương thức để kiểm tra OTP nhập vào
    public void checkOTP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredOtp = request.getParameter("otp");
        String otp = (String) request.getSession().getAttribute("otp");

        if (otp != null && otp.equals(enteredOtp)) {
            // Nếu OTP đúng, lưu trạng thái OTP đã nhập
            request.getSession().setAttribute("otpEntered", true);
            request.setAttribute("message", "Mã OTP đã được xác nhận thành công.");
            request.getRequestDispatcher("reset-password-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không chính xác.");
            request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
        }
    }
}
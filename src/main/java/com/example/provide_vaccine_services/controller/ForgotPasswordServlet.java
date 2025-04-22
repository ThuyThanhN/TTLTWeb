package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.OTPGenerator;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
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
                // Kiểm tra trạng thái tài khoản (nếu chưa xác thực)
                Users user = userDao.getUserByEmail(email);
                if (user != null && user.getStatus() == 0) {
                    // Nếu tài khoản chưa xác thực, thông báo yêu cầu xác thực tài khoản
                    request.setAttribute("error", "Tài khoản chưa được xác thực. Vui lòng xác thực tài khoản trước khi thực hiện reset mật khẩu.");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                // Tiến hành gửi OTP nếu tài khoản đã xác thực
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
}
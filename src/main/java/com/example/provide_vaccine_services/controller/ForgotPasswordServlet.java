package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.OTPGenerator;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

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
        LogDao logDao = new LogDao();

        try {
            // Kiểm tra xem email có tồn tại không
            if (userDao.isEmailExists(email)) {
                // Lấy user để kiểm tra trạng thái
                Users user = userDao.getUserByEmail(email);
                if (user != null && user.getStatus() == 0) {
                    // Log tài khoản chưa xác thực cố gắng reset mật khẩu
                    try {
                        logDao.insertLog("WARN", "Reset password attempt for unverified account", email, request.getRemoteAddr());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    request.setAttribute("error", "Tài khoản chưa được xác thực. Vui lòng xác thực tài khoản trước khi thực hiện reset mật khẩu.");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                // Tiến hành gửi OTP nếu tài khoản đã xác thực
                String otp = OTPGenerator.generateOTP();
                EmailSender.sendEmail(email, "Reset Password OTP", "Mã OTP của bạn là: " + otp);

                // Log gửi OTP thành công
                try {
                    logDao.insertLog("INFO", "Sent reset password OTP", email, request.getRemoteAddr());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Lưu OTP vào session
                request.getSession().setAttribute("otp", otp);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("otpCreatedTime", System.currentTimeMillis());

                request.setAttribute("message", "Đang gửi mã OTP đến email của bạn.");
                response.sendRedirect(request.getContextPath() + "/verify-reset-passwd");
            } else {
                // Log cố gắng reset mật khẩu với email không tồn tại
                try {
                    logDao.insertLog("WARN", "Reset password attempt for non-existent email", email, request.getRemoteAddr());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                request.setAttribute("error", "Email không tồn tại trong hệ thống.");
                request.getRequestDispatcher("reset-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Log lỗi khi xử lý reset mật khẩu
            try {
                logDao.insertLog("ERROR", "Error during reset password process for email: " + email + " - " + e.getMessage(),
                        email, request.getRemoteAddr());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }
    }}

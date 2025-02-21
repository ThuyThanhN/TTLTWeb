package com.example.provide_vaccine_services.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ValidateOtpServlet", value = "/verify-reset-passwd")
public class ValidateOtpServlet extends HttpServlet {


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

        // Kiểm tra OTP
        if (sessionOtp != null && sessionOtp.equals(enteredOtp)) {
            // OTP hợp lệ, chuyển đến trang update mật khẩu
            request.setAttribute("email", email);
            response.sendRedirect(request.getContextPath() + "/updatePasswd");
        } else {
            // OTP không hợp lệ, hiển thị thông báo lỗi trên trang hiện tại
            request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
        }
    }
}

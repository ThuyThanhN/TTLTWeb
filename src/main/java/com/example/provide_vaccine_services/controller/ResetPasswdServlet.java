package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdatePasswdServlet", value = "/updatePasswd")
public class ResetPasswdServlet extends HttpServlet {

    private final UserDao userDao = new UserDao(); // Khởi tạo DAO

    // Hàm kiểm tra xem thông tin email có tồn tại trong session hay không
    private boolean hasValidSession(HttpServletRequest request) {
        return request.getSession().getAttribute("email") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu không có email trong session
        if (!hasValidSession(request)) {
            response.sendRedirect("verify-reset-passwd.jsp"); // Chuyển hướng đến trang xác minh OTP
            return;
        }

        // Chuyển hướng đến trang nhập mật khẩu nếu email hợp lệ
        request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu không có email trong session
        if (!hasValidSession(request)) {
            response.sendRedirect("verify-reset-passwd.jsp"); // Chuyển hướng đến trang xác minh OTP
            return;
        }

        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("email");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        // Kiểm tra mật khẩu có khớp nhau không
        if (newPassword == null || confirmNewPassword == null || !newPassword.equals(confirmNewPassword)) {
            request.setAttribute("message", "Mật khẩu không khớp. Vui lòng thử lại.");
            request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
            return;
        }

        // Hash mật khẩu mới
        String hashedPassword = MD5Hash.hashPassword(newPassword);

        // Lấy userId từ email
        int userId = userDao.getUserIdByEmail(email);

        // Cập nhật mật khẩu
        if (userDao.updatePassword(userId, hashedPassword)) {
            request.getSession().invalidate(); // Hủy session để bảo mật
            response.sendRedirect("login.jsp"); // Chuyển hướng về trang đăng nhập
        } else {
            request.setAttribute("message", "Cập nhật mật khẩu thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
        }
    }
}

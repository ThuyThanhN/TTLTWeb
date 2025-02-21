package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", value = "/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang đổi mật khẩu
        HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
        if (session == null || session.getAttribute("user") == null) {
            // Chưa đăng nhập, chuyển hướng về trang đăng nhập
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Nếu đã đăng nhập, hiển thị trang đổi mật khẩu
        request.getRequestDispatcher("change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đảm bảo dữ liệu được mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");
        System.out.println("Current Password (plain): " + currentPassword);
        System.out.println("New Password (plain): " + newPassword);
        System.out.println("Confirm Password (plain): " + confirmNewPassword);


        // Kiểm tra giá trị null hoặc rỗng
        if (currentPassword == null || newPassword == null || confirmNewPassword == null ||
                currentPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmNewPassword.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
            return;
        }
        // Lấy thông tin người dùng hiện tại từ session
        HttpSession session = request.getSession(false);
        Users currentUser = (Users) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Kiểm tra mật khẩu hiện tại
        String hashedCurrentPassword = MD5Hash.hashPassword(currentPassword);
        System.out.println("Current Password (hashed from input): " + hashedCurrentPassword);

        if (!hashedCurrentPassword.equals(currentUser.getPassword())) {
            System.out.println("Mật khẩu mới và xác nhận không khớp!");

            request.setAttribute("message", "Mật khẩu hiện tại không chính xác!");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("message", "Mật khẩu mới và xác nhận không khớp!");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu mới
        String hashedNewPassword = MD5Hash.hashPassword(newPassword);
        System.out.println("New Password (hashed): " + hashedNewPassword);

        // Cập nhật mật khẩu trong cơ sở dữ liệu
        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.updatePassword(currentUser.getId(), hashedNewPassword);

        if (isUpdated) {
            System.out.println("Đổi mật khẩu thành công!");

            // Cập nhật thành công, cập nhật session
            currentUser.setPassword(hashedNewPassword);
            session.setAttribute("user", currentUser);
            request.setAttribute("message", "Đổi mật khẩu thành công!");
        } else {
            System.out.println("Đổi mật khẩu thất bại!");

            request.setAttribute("message", "Đổi mật khẩu thất bại!");
        }

        // Trả về trang đổi mật khẩu
        request.getRequestDispatcher("change-password.jsp").forward(request, response);
    }
}


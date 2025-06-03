package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.LogDao;
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
    private final LogDao logDao = new LogDao(); // Khởi tạo LogDao

    // Hàm kiểm tra xem thông tin email có tồn tại trong session hay không
    private boolean hasValidSession(HttpServletRequest request) {
        return request.getSession().getAttribute("email") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu không có email trong session thì chuyển hướng đến trang xác minh OTP
        if (!hasValidSession(request)) {
            response.sendRedirect("verify-reset-passwd.jsp");
            return;
        }

        // Nếu có email trong session, chuyển hướng đến trang nhập mật khẩu mới
        request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIp = request.getRemoteAddr();

        // Kiểm tra nếu không có email trong session thì chuyển hướng đến trang xác minh OTP
        if (!hasValidSession(request)) {
            response.sendRedirect("verify-reset-passwd.jsp");
            return;
        }

        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("email");
        // Lấy mật khẩu mới và xác nhận mật khẩu từ request
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        // Kiểm tra mật khẩu có khớp nhau không
        if (newPassword == null || confirmNewPassword == null || !newPassword.equals(confirmNewPassword)) {
            request.setAttribute("message", "Mật khẩu không khớp. Vui lòng thử lại.");
            request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
            return;
        }

        // Hash mật khẩu mới bằng MD5
        String hashedPassword = MD5Hash.hashPassword(newPassword);

        // Lấy userId từ email
        int userId = userDao.getUserIdByEmail(email);

        // Cập nhật mật khẩu cho user trong database
        if (userDao.updatePassword(userId, hashedPassword)) {
            // Ghi log thành công cập nhật mật khẩu
            try {
                logDao.insertLog("INFO", "User password updated successfully", email, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Hủy session để bảo mật
            request.getSession().invalidate();
            // Chuyển hướng về trang đăng nhập
            response.sendRedirect("login.jsp");
        } else {
            // Ghi log thất bại khi cập nhật mật khẩu
            try {
                logDao.insertLog("ERROR", "Failed to update user password", email, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Hiển thị thông báo lỗi và chuyển tiếp về trang nhập lại mật khẩu
            request.setAttribute("message", "Cập nhật mật khẩu thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher("update-passwd.jsp").forward(request, response);
        }
    }
}

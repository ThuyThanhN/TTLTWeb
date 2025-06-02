package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại nếu có, không tạo mới
        HttpSession session = request.getSession(false);
        String userEmail = null;
        String userIp = request.getRemoteAddr();

        if (session != null) {
            Object userObj = session.getAttribute("user");
            // Kiểm tra user trong session và lấy email
            if (userObj != null && userObj instanceof com.example.provide_vaccine_services.dao.model.Users) {
                userEmail = ((com.example.provide_vaccine_services.dao.model.Users) userObj).getEmail();
            }
            // Ghi log đăng xuất nếu có email
            if (userEmail != null) {
                LogDao logDao = new LogDao();
                try {
                    logDao.insertLog("INFO", "User logged out", userEmail, userIp);
                } catch (Exception e) {
                    e.printStackTrace(); // Log lỗi nhưng không ngăn chặn đăng xuất
                }
            }

            // Hủy session để đăng xuất
            session.invalidate();
        }

        // Chuyển hướng về trang chủ
        response.sendRedirect("index");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gọi lại doGet để xử lý logout với POST
        doGet(request, response);
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "lockAccount", value = "/lockAccount")
public class LockAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không có xử lý GET
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Users users = (Users) session.getAttribute("user");
        if (users == null) {
            throw new ServletException("User is not logged in");
        }

        int userId = users.getId();
        String userEmail = users.getEmail();
        String userIp = request.getRemoteAddr();

        UserDao dao = new UserDao();
        LogDao logDao = new LogDao();

        boolean success = dao.lockAccount(userId);

        if (success) {
            // Ghi log khóa tài khoản thành công
            try {
                logDao.insertLog("INFO", "User account locked successfully", userEmail, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Hủy session hiện tại để đăng xuất người dùng
            request.getSession().invalidate();
            response.sendRedirect("login");
        } else {
            // Ghi log khóa tài khoản thất bại
            try {
                logDao.insertLog("ERROR", "Failed to lock user account", userEmail, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("lock_account.jsp");
        }
    }
}

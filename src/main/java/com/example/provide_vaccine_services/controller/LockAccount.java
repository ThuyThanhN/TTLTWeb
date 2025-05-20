package com.example.provide_vaccine_services.controller;

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Users users = (Users) session.getAttribute("user");
        if (users == null) {
            throw new ServletException("User is not logged in");
        }
        int userId = users.getId();
        UserDao dao = new UserDao();
        boolean success = dao.lockAccount(userId);

        if (success) {
            request.getSession().invalidate(); // Hủy session hiện tại (đăng xuất)
            response.sendRedirect("login");
        } else {
            response.sendRedirect("lock_account.jsp");
        }
    }
}

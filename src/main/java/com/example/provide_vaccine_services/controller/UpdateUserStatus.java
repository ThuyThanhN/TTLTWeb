package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateUserStatus", value = "/admin/updateUserStatus")
public class UpdateUserStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));

        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.updateStatus(id, status);

        if (isUpdated) {
            try {
                logDao.insertLog("INFO", "Order status updated successfully. Order ID: " + id + ", New status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"success\"}");
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update order status. Order ID: " + id + ", Attempted status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"error\"}");
        }
    }
}

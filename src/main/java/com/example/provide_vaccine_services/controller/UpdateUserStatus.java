package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateOrderStatus", value = "/admin/updateOrderStatus")
public class UpdateUserStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String status = request.getParameter("status");

        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.updateStatus(orderId, status);

        if (isUpdated) {
            try {
                logDao.insertLog("INFO", "Order status updated successfully. Order ID: " + orderId + ", New status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"success\"}");
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update order status. Order ID: " + orderId + ", Attempted status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"error\"}");
        }
    }
}

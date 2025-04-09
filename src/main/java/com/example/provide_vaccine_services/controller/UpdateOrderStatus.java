package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateOrderStatus", value = "/admin/updateOrderStatus")
public class UpdateOrderStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String status = request.getParameter("status");

        OrderDao orderDao = new OrderDao();
        boolean isUpdated = orderDao.updateStatus(orderId, status);

        if (isUpdated) {
            response.getWriter().write("{\"message\": \"success\"}");
        } else {
            response.getWriter().write("{\"message\": \"error\"}");
        }
    }
}

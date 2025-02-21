package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateOrderStatus", value = "/updateOrderStatus")
public class UpdateOrderStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String status = request.getParameter("status");

        System.out.println("orderId:" + orderId);
        System.out.println("status:" + status);

        OrderDao orderDao = new OrderDao();
        boolean isUpdated = orderDao.updateStatus(orderId, status);

        if (isUpdated) {
            System.out.println("Thanh cong");
        } else {
            System.out.println("That bai");
        }

        response.sendRedirect("table-data-order");
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OrderDetailServlet", value = "/appointment-slip")
public class OrderDetailServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = new OrderDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Map<String, Object> orderData = orderDao.getAppointmentDetails(id);

            // Kiểm tra contactFullname và contactRelationship trong console
            System.out.println("contactFullname: " + orderData.get("contactFullname"));
            System.out.println("contactRelationship: " + orderData.get("contactRelationship"));

            if (orderData == null || orderData.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment details not found");
                return;
            }

            // Truyền dữ liệu vào request
            request.setAttribute("order", orderData.get("order"));
            request.setAttribute("patient", orderData.get("patient"));
            request.setAttribute("center", orderData.get("center"));
            request.setAttribute("vaccine", orderData.get("vaccine"));
            request.setAttribute("contactFullname", orderData.get("contactFullname"));
            request.setAttribute("contactPhone", orderData.get("contactPhone"));
            request.setAttribute("contactRelationship", orderData.get("contactRelationship")); // Thêm contactRelationship

            request.getRequestDispatcher("appointment-slip.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID format");
        }
    }
}
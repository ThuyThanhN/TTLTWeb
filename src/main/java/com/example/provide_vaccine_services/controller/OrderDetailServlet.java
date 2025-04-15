package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
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

        System.out.println("Received idParam: " + idParam); // Debug: Kiểm tra tham số id từ request

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            System.out.println("Parsed id: " + id); // Debug: Kiểm tra ID sau khi parse

            Map<String, Object> orderData = orderDao.getAppointmentDetails(id);
            System.out.println("orderData received: " + orderData); // Debug: Kiểm tra dữ liệu trả về từ DAO

            if (orderData == null || orderData.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment details not found");
                return;
            }

            // Debug: Kiểm tra từng đối tượng mà bạn sẽ truyền vào request
            System.out.println("Order object: " + orderData.get("order"));
            System.out.println("Patient object: " + orderData.get("patient"));
            System.out.println("Center object: " + orderData.get("center"));
            System.out.println("Vaccine objects: " + orderData.get("vaccines"));  // Kiểm tra tất cả vắc xin

            // Truyền dữ liệu vào request
            request.setAttribute("order", orderData.get("order"));
            request.setAttribute("patient", orderData.get("patient"));
            request.setAttribute("center", orderData.get("center"));
            request.setAttribute("vaccines", orderData.get("vaccines"));  // Truyền danh sách vắc xin vào request
            request.setAttribute("contactFullname", orderData.get("contactFullname"));
            request.setAttribute("contactPhone", orderData.get("contactPhone"));
            request.setAttribute("contactRelationship", orderData.get("contactRelationship"));

            // Truyền tên gói vắc xin vào request
            request.setAttribute("vaccinePackageName", orderData.get("vaccinePackageName"));

            // Debug: Kiểm tra trước khi forward
            System.out.println("Forwarding to appointment-slip.jsp");
            request.getRequestDispatcher("appointment-slip.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Order ID: " + e.getMessage()); // Debug: Xử lý lỗi khi parse ID
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID format");
        }
    }
}

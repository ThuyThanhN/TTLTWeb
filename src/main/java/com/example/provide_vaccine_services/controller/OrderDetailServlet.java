package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
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
    private LogDao logDao;

    @Override
    public void init() {
        orderDao = new OrderDao();
        logDao = new LogDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String userIp = request.getRemoteAddr();

        // Log nhận tham số id từ request
        try {
            logDao.insertLog("INFO", "Received idParam: " + idParam, "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            // Log id đã parse thành công
            try {
                logDao.insertLog("INFO", "Parsed id: " + id, "anonymous", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Map<String, Object> orderData = orderDao.getAppointmentDetails(id);

            // Log dữ liệu trả về từ DAO
            try {
                logDao.insertLog("INFO", "orderData received for id " + id + ": " + orderData, "anonymous", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (orderData == null || orderData.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment details not found");
                return;
            }

            // Log các đối tượng quan trọng sẽ gửi sang JSP
            try {
                logDao.insertLog("INFO", "Order object: " + orderData.get("order"), "anonymous", userIp);
                logDao.insertLog("INFO", "Patient object: " + orderData.get("patient"), "anonymous", userIp);
                logDao.insertLog("INFO", "Center object: " + orderData.get("center"), "anonymous", userIp);
                logDao.insertLog("INFO", "Vaccine objects: " + orderData.get("vaccines"), "anonymous", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Truyền dữ liệu vào request
            request.setAttribute("order", orderData.get("order"));
            request.setAttribute("patient", orderData.get("patient"));
            request.setAttribute("center", orderData.get("center"));
            request.setAttribute("vaccines", orderData.get("vaccines"));
            request.setAttribute("contactFullname", orderData.get("contactFullname"));
            request.setAttribute("contactPhone", orderData.get("contactPhone"));
            request.setAttribute("contactRelationship", orderData.get("contactRelationship"));
            request.setAttribute("paymentStatus", orderData.get("paymentStatus"));
            request.setAttribute("vaccinePackageName", orderData.get("vaccinePackageName"));

            // Log trước khi forward
            try {
                logDao.insertLog("INFO", "Forwarding to appointment-slip.jsp for id: " + id, "anonymous", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.getRequestDispatcher("appointment-slip.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            try {
                logDao.insertLog("ERROR", "Error parsing Order ID: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID format");
        }
    }
}

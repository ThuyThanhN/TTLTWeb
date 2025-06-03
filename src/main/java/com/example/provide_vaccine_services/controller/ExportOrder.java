package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExportOrder", value = "/admin/exportOrder")
public class ExportOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        OrderDao orderDao = new OrderDao();

        // Ghi log bắt đầu xuất dữ liệu đơn hàng
        System.out.println("ExportOrder: Starting to export order data from IP: " + userIp);
        try {
            logDao.insertLog("INFO", "Start exporting order data", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> orderData = orderDao.export();

        // Log số lượng đơn hàng xuất ra
        System.out.println("ExportOrder: Number of orders exported: " + orderData.size());
        try {
            logDao.insertLog("INFO", "Exported " + orderData.size() + " orders", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển đổi dữ liệu sang JSON
        String json = new Gson().toJson(orderData);

        // Ghi JSON vào response
        response.getWriter().write(json);

        // Log hoàn thành xuất dữ liệu
        System.out.println("ExportOrder: Export order data completed successfully from IP: " + userIp);
        try {
            logDao.insertLog("INFO", "Completed exporting order data", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong servlet này
    }
}

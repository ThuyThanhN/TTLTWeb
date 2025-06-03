package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.OrderDao;
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

@WebServlet(name = "DisplayDetailOrder", value = "/admin/displayDetailOrder")
public class DisplayDetailOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            logDao.insertLog("INFO", "Nhận yêu cầu lấy chi tiết đơn hàng id: " + id, "system", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OrderDao orderDao = new OrderDao();
        List<Map<String, Object>> orderDetails = orderDao.getOrderDetailById(id);

        try {
            logDao.insertLog("INFO", "Lấy chi tiết đơn hàng, số bản ghi: " + orderDetails.size(), "system", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String json = gson.toJson(orderDetails);

        response.getWriter().write(json);

        try {
            logDao.insertLog("INFO", "Trả về dữ liệu JSON chi tiết đơn hàng id " + id, "system", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST
    }
}

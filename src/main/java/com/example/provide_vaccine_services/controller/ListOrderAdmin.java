package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.OrderDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListOrderAdmin", value = "/admin/table-data-order")
public class ListOrderAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            OrderDao orderDao = new OrderDao();
            // Lấy danh sách đơn hàng
            List<Map<String, Object>> orders = orderDao.getOrder();
            // Đặt danh sách đơn hàng vào request
            request.setAttribute("orders", orders);

            // Ghi log truy cập danh sách đơn hàng
            logDao.insertLog("INFO", "Admin accessed order list", "anonymous", userIp);

            // Chuyển tiếp đến trang hiển thị danh sách đơn hàng
            request.getRequestDispatcher("table-data-order.jsp").forward(request, response);

        } catch (SQLException e) {
            // Ghi log lỗi khi lấy danh sách đơn hàng
            try {
                logDao.insertLog("ERROR", "Error loading order list: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách đơn hàng");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

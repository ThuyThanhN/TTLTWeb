package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListAppointment", value = "/my-appointments")
public class ListAppointment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại
        HttpSession session = request.getSession();
        // Lấy thông tin người dùng từ session
        Users user = (Users) session.getAttribute("user");

        // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();
        String userEmail = user.getEmail();
        String userIp = request.getRemoteAddr();

        LogDao logDao = new LogDao();

        try {
            // Khởi tạo DAO để lấy danh sách đơn hàng theo userId
            OrderDao orderDao = new OrderDao();
            List<Map<String, Object>> orders = orderDao.getOrderByUserId(userId);

            // Ghi log việc truy cập danh sách lịch hẹn cá nhân
            logDao.insertLog("INFO", "User accessed their appointment list", userEmail, userIp);

            // Đặt dữ liệu đơn hàng vào request để hiển thị trong JSP
            request.setAttribute("orders", orders);

            // Chuyển tiếp đến trang my-appointments.jsp
            request.getRequestDispatcher("my-appointments.jsp").forward(request, response);
        } catch (SQLException e) {
            // Ghi log lỗi khi lấy dữ liệu lịch hẹn
            try {
                logDao.insertLog("ERROR", "Error retrieving appointments: " + e.getMessage(), userEmail, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách lịch hẹn");
        }
    }
}

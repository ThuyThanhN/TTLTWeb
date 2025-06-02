package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListCustomerAdmin", value = "/admin/table-data-user")
public class ListCustomerAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách khách hàng
            UserDao userDao = new UserDao();
            List<Users> customs = userDao.getAllByCus();

            // Đặt danh sách khách hàng vào request để hiển thị
            request.setAttribute("customs", customs);

            // Ghi log việc truy cập trang danh sách khách hàng
            logDao.insertLog("INFO", "Admin accessed customer list page", "anonymous", userIp);

            // Chuyển tiếp đến trang table-data-user.jsp
            request.getRequestDispatcher("table-data-user.jsp").forward(request, response);
        } catch (SQLException e) {
            // Ghi log lỗi khi truy cập danh sách khách hàng
            try {
                logDao.insertLog("ERROR", "Error loading customer list: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu khách hàng");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong trường hợp này
    }
}

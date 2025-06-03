package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.logs;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListLogsAdmin", value = "/admin/table-data-logs")
public class ListLogsAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy toàn bộ log từ cơ sở dữ liệu
            List<logs> logs = logDao.getAll();
            // Đặt logs vào request để hiển thị lên JSP
            request.setAttribute("logs", logs);

            // Ghi log việc truy cập trang xem logs
            logDao.insertLog("INFO", "Admin accessed logs page", "anonymous", userIp);

            // Chuyển tiếp đến trang table-data-logs.jsp
            request.getRequestDispatcher("table-data-logs.jsp").forward(request, response);
        } catch (SQLException e) {
            // Ghi log lỗi khi truy cập logs
            try {
                logDao.insertLog("ERROR", "Error loading logs: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu logs");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong trường hợp này
    }
}

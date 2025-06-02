package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.Centers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListCenterAdmin", value = "/admin/table-data-center")
public class ListCenterAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách trung tâm tiêm chủng
            CenterDao centerDao = new CenterDao();
            List<Centers> centers = centerDao.getAll();

            // Đặt danh sách trung tâm vào request để hiển thị
            request.setAttribute("centers", centers);

            // Ghi log việc truy cập trang danh sách trung tâm
            logDao.insertLog("INFO", "Admin accessed center list page", "anonymous", userIp);

            // Chuyển tiếp đến trang table-data-center.jsp
            request.getRequestDispatcher("table-data-center.jsp").forward(request, response);
        } catch (SQLException e) {
            // Ghi log lỗi khi truy cập danh sách trung tâm
            try {
                logDao.insertLog("ERROR", "Error loading center list: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu trung tâm");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong trường hợp này
    }
}

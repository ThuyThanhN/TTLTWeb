package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListStaffAdmin", value = "/admin/table-data-staff")
public class ListStaffAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách nhân viên
            UserDao userDao = new UserDao();
            List<Map<String, Object>> staffs = userDao.getAllByStaff();

            // Đặt dữ liệu nhân viên vào request
            request.setAttribute("staffs", staffs);

            // Ghi log truy cập danh sách nhân viên
            logDao.insertLog("INFO", "Accessed staff list for admin", "anonymous", userIp);

            // Chuyển tiếp đến trang hiển thị danh sách nhân viên
            request.getRequestDispatcher("table-data-staff.jsp").forward(request, response);

        } catch (Exception e) {
            // Ghi log lỗi khi tải danh sách nhân viên
            try {
                logDao.insertLog("ERROR", "Error loading staff list for admin: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách nhân viên");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

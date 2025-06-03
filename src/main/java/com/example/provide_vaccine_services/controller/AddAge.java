package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddAge", value = "/admin/addAge")
public class AddAge extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET chưa được sử dụng, ghi log đơn giản
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        try {
            logDao.insertLog("INFO", "GET request received at /admin/addAge but no action defined", null, userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        String name = request.getParameter("name");

        try {
            logDao.insertLog("INFO", "Received POST request to add age group with name: " + name, null, userIp);

            AgeGroupDao dao = new AgeGroupDao();
            AgeGroups ageGroup = new AgeGroups(name);
            dao.insert(ageGroup);

            logDao.insertLog("INFO", "Successfully inserted age group: " + name, null, userIp);

            response.sendRedirect("form-add-vacxin");
        } catch (SQLException e) {
            try {
                logDao.insertLog("ERROR", "Failed to insert age group: " + name + " - " + e.getMessage(), null, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm nhóm tuổi mới.");
        }
    }
}

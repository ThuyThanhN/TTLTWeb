package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.DisaseGroupDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddDisase", value = "/admin/addDisase")
public class AddDisase extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET chưa dùng, ghi log đơn giản
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        try {
            logDao.insertLog("INFO", "GET request received at /admin/addDisase but no action defined", null, userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            logDao.insertLog("INFO", "Received POST request to add disease group with name: " + name, null, userIp);

            DisaseGroupDao dao = new DisaseGroupDao();
            DisaseGroups disaseGroup = new DisaseGroups(name);
            dao.insert(disaseGroup);

            logDao.insertLog("INFO", "Successfully inserted new disease group: " + name, null, userIp);

            response.sendRedirect("form-add-vacxin");
        } catch (SQLException e) {
            try {
                logDao.insertLog("ERROR", "Failed to insert disease group: " + name + " - " + e.getMessage(), null, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm nhóm bệnh mới.");
        }
    }
}

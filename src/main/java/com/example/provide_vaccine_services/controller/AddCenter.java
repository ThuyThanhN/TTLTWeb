package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.Centers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddCenter", value = "/admin/addCenter")
public class AddCenter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET chưa được sử dụng, ghi log thông tin đơn giản
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        try {
            logDao.insertLog("INFO", "GET request received at /admin/addCenter but no action defined", null, userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        String name = request.getParameter("center-name");
        String address = request.getParameter("center-address");
        String province = request.getParameter("center-province");
        String district = request.getParameter("center-district");
        String ward = request.getParameter("center-ward");
        String phone = request.getParameter("center-phone");

        try {
            logDao.insertLog("INFO", "Received POST request to add center: " + name, null, userIp);

            Centers center = new Centers(name, address, province, district, ward, phone);
            CenterDao dao = new CenterDao();
            int insertId = dao.insert(center);

            if (insertId > 0) {
                logDao.insertLog("INFO", "Successfully inserted center with ID: " + insertId, null, userIp);
                response.getWriter().write("{\"status\":\"success\", \"id\":" + insertId + "}");
            } else {
                logDao.insertLog("WARN", "Failed to insert center: " + name, null, userIp);
                response.getWriter().write("{\"status\":\"error\"}");
            }
        } catch (SQLException e) {
            try {
                logDao.insertLog("ERROR", "Error inserting center: " + name + " - " + e.getMessage(), null, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }
}

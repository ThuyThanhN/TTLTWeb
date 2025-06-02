package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.Centers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateCenter", value = "/admin/updateCenter")
public class UpdateCenter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation needed for GET as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request character encoding to UTF-8 to handle special characters properly
        request.setCharacterEncoding("UTF-8");
        // Set response content type to JSON and encoding to UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Parse center details from request parameters
        int centerId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("center-name");
        String address = request.getParameter("center-address");
        String province = request.getParameter("center-province");
        String district = request.getParameter("center-district");
        String ward = request.getParameter("center-ward");
        String phone = request.getParameter("center-phone");

        // Create Center object with updated information
        Centers center = new Centers(centerId, name, address, province, district, ward, phone);
        CenterDao centerDao = new CenterDao();

        // Initialize LogDao for logging activities
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr(); // Get user IP address for log

        // Perform update operation in database
        int result = centerDao.update(center);

        // Write JSON response back to client based on update result
        response.getWriter().write("{\"status\": \"" + (result > 0 ? "success" : "error") + "\", " +
                "\"name\": \"" + name + "\"," +
                "\"address\": \"" + address + "\"," +
                "\"province\": \"" + province + "\"," +
                "\"district\": \"" + district + "\"," +
                "\"ward\": \"" + ward + "\"," +
                "\"phone\": \"" + phone + "\"}");

        // Log success or failure of update operation with appropriate log level
        if (result > 0) {
            try {
                logDao.insertLog("INFO", "Center updated successfully: ID=" + centerId + ", Name=" + name, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if logging fails, but do not interrupt flow
            }
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update center: ID=" + centerId + ", Name=" + name, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

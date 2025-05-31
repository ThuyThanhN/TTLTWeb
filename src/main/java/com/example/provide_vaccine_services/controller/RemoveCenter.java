package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveCenter", value = "/admin/removeCenter")
public class RemoveCenter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No GET implementation as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response encoding and content type for UTF-8 JSON
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Parse center ID from request
            String id = request.getParameter("id");
            int idCenter = Integer.parseInt(id);

            // Initialize CenterDao and delete center by ID
            CenterDao centerDao = new CenterDao();
            centerDao.deleteCenter(idCenter);

            // Log successful deletion
            try {
                logDao.insertLog("INFO", "Center deleted successfully: ID=" + idCenter, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace(); // Logging failure should not interrupt flow
            }

            // Send JSON success response
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            // Log failure to delete center
            try {
                logDao.insertLog("ERROR", "Failed to delete center", "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send JSON error response
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}

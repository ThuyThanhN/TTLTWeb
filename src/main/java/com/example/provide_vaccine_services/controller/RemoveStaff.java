package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveStaff", value = "/admin/removeStaff")
public class RemoveStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation needed for GET as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response character encoding and content type to support UTF-8 and JSON
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        String id = request.getParameter("id");
        try {
            // Parse staff ID from request parameter
            int idS = Integer.parseInt(id);
            UserDao userDao = new UserDao();

            // Delete staff from database by ID
            userDao.delete(idS);

            // Log successful staff deletion
            try {
                logDao.insertLog("INFO", "Staff deleted successfully: ID=" + idS, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace(); // Log failure shouldn't interrupt flow
            }

            // Send JSON success response
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            // Log failure to delete staff
            try {
                logDao.insertLog("ERROR", "Failed to delete staff: ID=" + id, "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send JSON error response
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemovePackage", value = "/admin/removePackage")
public class RemovePackage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation needed for GET as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response encoding and content type
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        String idStr = request.getParameter("id");

        try {
            // Parse package ID from request parameter
            int id = Integer.parseInt(idStr);

            VaccinePackageDao dao = new VaccinePackageDao();
            // Delete package from database
            dao.delete(id);

            // Log successful deletion
            try {
                logDao.insertLog("INFO", "Vaccine package deleted successfully: ID=" + id, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace(); // Log failure should not stop flow
            }

            // Send JSON success response
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            // Log failure to delete package
            try {
                logDao.insertLog("ERROR", "Failed to delete vaccine package: ID=" + idStr, "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send JSON error response
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveVaccine", value = "/admin/removeVaccine")
public class RemoveVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation needed for GET as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response character encoding and content type
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Parse vaccine ID from request parameter
            int idVaccine = Integer.parseInt(request.getParameter("id"));
            System.out.println("idVacccine: " + idVaccine);

            // Initialize VaccineDao and delete vaccine by ID
            VaccineDao vaccineDao = new VaccineDao();
            vaccineDao.deleteByIdVaccine(idVaccine);

            // Log successful deletion
            try {
                logDao.insertLog("INFO", "Vaccine deleted successfully: ID=" + idVaccine, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace(); // Log error but do not interrupt flow
            }

            // Send JSON success response
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            // Log deletion failure
            try {
                logDao.insertLog("ERROR", "Failed to delete vaccine", "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send JSON error response
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}

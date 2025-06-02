package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveSupplier", value = "/admin/removeSupplier")
public class RemoveSupplier extends HttpServlet {

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

        String id = request.getParameter("id");

        try {
            int idS = Integer.parseInt(id);
            SupplierDao supplierDao = new SupplierDao();
            VaccineDao vaccineDao = new VaccineDao();

            // Delete vaccines associated with supplier
            vaccineDao.deleteByIdSupplier(idS);
            // Delete supplier
            supplierDao.deleteSupplier(idS);

            // Log successful deletion
            try {
                logDao.insertLog("INFO", "Supplier and related vaccines deleted successfully: Supplier ID=" + idS, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Send JSON success response
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            // Log failure to delete
            try {
                logDao.insertLog("ERROR", "Failed to delete supplier or related vaccines. Supplier ID=" + id, "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send JSON error response
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateSupplier", value = "/admin/updateSupplier")
public class UpdateSupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation as per original
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        int supplierId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("supplier-name");
        String country = request.getParameter("supplier-country");

        SupplierDao supplierDao = new SupplierDao();
        Suppliers supplier = new Suppliers(supplierId, name, country);
        int result = supplierDao.update(supplier);

        if (result > 0) {
            try {
                logDao.insertLog("INFO", "Supplier updated successfully: ID=" + supplierId + ", Name=" + name + ", Country=" + country, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update supplier: ID=" + supplierId + ", Name=" + name + ", Country=" + country, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.getWriter().write("{\"status\": \"" + (result > 0 ? "success" : "error")
                + "\", \"name\": \"" + name + "\", \"country\": \"" + country + "\"}");
    }
}

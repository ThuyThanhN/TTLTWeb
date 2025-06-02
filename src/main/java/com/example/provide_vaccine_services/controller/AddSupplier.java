package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "addSupplier", value = "/admin/addSupplier")
public class AddSupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý GET trong servlet này
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        String name = request.getParameter("supplier-name");
        String country = request.getParameter("supplier-country");

        SupplierDao supplierDao = new SupplierDao();
        Suppliers supplier = new Suppliers(name, country);
        int insertId = supplierDao.insert(supplier);

        try {
            if (insertId > 0) {
                logDao.insertLog("INFO", "Thêm nhà cung cấp thành công với id: " + insertId, name, userIp);
                response.getWriter().write("{\"status\":\"success\", \"id\":" + insertId + "}");
            } else {
                logDao.insertLog("ERROR", "Thêm nhà cung cấp thất bại", name, userIp);
                response.getWriter().write("{\"status\":\"error\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi ghi log\"}");
        }
    }
}

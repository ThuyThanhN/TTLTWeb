package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListSupplierAdmin", value = "/admin/table-data-supplier")
public class ListSupplierAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách nhà cung cấp
        SupplierDao supplierDao = new SupplierDao();
        List<Suppliers> suppliers = supplierDao.getAll();

        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("table-data-supplier.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

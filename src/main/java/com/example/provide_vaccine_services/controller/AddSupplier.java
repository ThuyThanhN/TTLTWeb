package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "addSupplier", value = "/addSupplier")
public class AddSupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("supplier-name");
        String country = request.getParameter("supplier-country");

        SupplierDao supplierDao = new SupplierDao();
        Suppliers supplier = new Suppliers(name, country);
        supplierDao.insert(supplier);

        response.sendRedirect("table-data-supplier");
    }
}

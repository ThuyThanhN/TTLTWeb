package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.OrderDetailDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveSupplier", value = "/removeSupplier")
public class RemoveSupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int idS = Integer.parseInt(id);
        SupplierDao supplierDao = new SupplierDao();
        VaccineDao vaccineDao = new VaccineDao();

        vaccineDao.deleteByIdSupplier(idS);
        supplierDao.deleteSupplier(idS);

        response.sendRedirect("table-data-supplier");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

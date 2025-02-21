package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddPackage", value = "/addPackage")
public class AddPackage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("nameP");
        String description = request.getParameter("description-name");

        VaccinePackageDao vpDao = new VaccinePackageDao();
        VaccinePackages vp = new VaccinePackages(name, description);
        vpDao.insert(vp);

        response.sendRedirect("table-data-vax-package");
    }
}


package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemovePackage", value = "/admin/removePackage")
public class RemovePackage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        VaccinePackageDao dao = new VaccinePackageDao();
        dao.delete(id);

        response.sendRedirect("table-data-vax-package");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


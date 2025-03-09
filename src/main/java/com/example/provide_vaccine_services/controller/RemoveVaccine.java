package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveVaccine", value = "/admin/removeVaccine")
public class RemoveVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int idVaccine = Integer.parseInt(id);

        System.out.println("idVacccine: " + idVaccine);
        VaccineDao vaccineDao = new VaccineDao();
        vaccineDao.deleteByIdVaccine(idVaccine);
        response.sendRedirect("table-data-vacxin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListVaccineAdmin", value = "/admin/table-data-vacxin")
public class ListVaccineAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách vắc xin
        VaccineDao vaccine = new VaccineDao();
        List<Vaccines> vaccines = vaccine.getAll();

        request.setAttribute("vaccines", vaccines);
        request.getRequestDispatcher("table-data-vacxin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


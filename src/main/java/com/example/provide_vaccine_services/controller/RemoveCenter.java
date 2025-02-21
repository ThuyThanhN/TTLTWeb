package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveCenter", value = "/removeCenter")
public class RemoveCenter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int idCenter = Integer.parseInt(id);
        CenterDao centerDao = new CenterDao();
        centerDao.deleteCenter(idCenter);
        response.sendRedirect("table-data-center");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


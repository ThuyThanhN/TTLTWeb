package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.model.Centers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListCenterAdmin", value = "/table-data-center")
public class ListCenterAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách nhà cung cấp
        CenterDao centerDao = new CenterDao();
        List<Centers> centers = centerDao.getAll();

        request.setAttribute("centers", centers);
        request.getRequestDispatcher("table-data-center.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


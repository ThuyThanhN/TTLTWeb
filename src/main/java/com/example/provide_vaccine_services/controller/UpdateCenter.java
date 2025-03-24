package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.model.Centers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateCenter", value = "/admin/updateCenter")
public class UpdateCenter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int centerId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("center-name");
        String address = request.getParameter("center-address");
        String province = request.getParameter("center-province");
        String district = request.getParameter("center-district");
        String ward = request.getParameter("center-ward");
        String phone = request.getParameter("center-phone");


        CenterDao centerDao = new CenterDao();
        Centers center = new Centers(centerId, name, address, province, district, ward, phone);
        int result = centerDao.update(center);

        response.getWriter().write("{\"status\": \"" + (result > 0 ? "success" : "error") + "\", " +
                "\"name\": \"" + name + "\"," +
                "\"address\": \"" + address + "\"," +
                "\"province\": \"" + province + "\"," +
                "\"district\": \"" + district + "\"," +
                "\"ward\": \"" + ward + "\"," +
                "\"phone\": \"" + phone + "\"}");
    }
}


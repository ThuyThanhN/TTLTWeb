package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.DisaseGroupDao;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddAge", value = "/addAge")
public class AddAge extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        AgeGroupDao dao = new AgeGroupDao();
        AgeGroups age = new AgeGroups(name);
        dao.insert(age);

        response.sendRedirect("form-add-vacxin");
    }
}


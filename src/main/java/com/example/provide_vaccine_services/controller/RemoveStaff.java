package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveStaff", value = "/removeStaff")
public class RemoveStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int idS = Integer.parseInt(id);
        UserDao userDao = new UserDao();
        userDao.delete(idS);
        response.sendRedirect("table-data-staff");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "UpdateStaff", value = "/admin/updateStaff")
public class UpdateStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        System.out.println("Gender value: " + gender);

        String ident = request.getParameter("ident");
        String date = request.getParameter("date");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String pass = request.getParameter("password");

        int idStaff = Integer.parseInt(id);
        int roleValue = role.equals("Admin") ? 1 : 2;
        Date sqlDate = Date.valueOf(date);

        UserDao userDao = new UserDao();
        Users user = new Users(idStaff, name, gender, ident, sqlDate,  address,
                province, district, ward, phone, email, pass,  roleValue);
        userDao.update(user);
        response.sendRedirect("table-data-staff");
    }
}


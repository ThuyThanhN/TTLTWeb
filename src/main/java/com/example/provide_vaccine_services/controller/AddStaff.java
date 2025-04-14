package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "AddStaff", value = "/admin/addStaff")
public class AddStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("fullname");
        String gender = request.getParameter("gender");
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

        int roleValue = role.equals("Admin") ? 1 : 2;
        Date sqlDate = Date.valueOf(date);

        // Ma hoa mat khau
        String hashedPassword = MD5Hash.hashPassword(pass);

        Users user = new Users(name, gender, ident, sqlDate,  address,
                province, district, ward, phone, email, hashedPassword,  roleValue);
        UserDao dao = new UserDao();
        int insertId = dao.insertStaff(user);
        // json
        if (insertId > 0) {
            response.getWriter().write("{\"status\":\"success\", \"id\":" + insertId + "}");
        } else {
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }
}

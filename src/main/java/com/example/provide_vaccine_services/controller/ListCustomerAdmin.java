package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListCustomerAdmin", value = "/admin/table-data-user")
public class ListCustomerAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách nhà cung cấp
        UserDao userDao = new UserDao();
        List<Users> customs = userDao.getAllByCus();

        request.setAttribute("customs", customs);
        request.getRequestDispatcher("table-data-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


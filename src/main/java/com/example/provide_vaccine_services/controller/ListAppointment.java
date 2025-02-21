package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListAppointment", value = "/my-appointments")
public class ListAppointment extends  HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        int userId = user.getId();
        System.out.println(userId);

        OrderDao orderDao = new OrderDao();
        List<Map<String, Object>> orders = orderDao.getOrderByUserId(userId);
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("my-appointments.jsp").forward(request, response);
    }
}

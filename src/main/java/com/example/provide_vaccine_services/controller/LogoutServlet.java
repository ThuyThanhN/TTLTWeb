package com.example.provide_vaccine_services.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại, nếu tồn tại
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Hủy session
            session.invalidate();
        }

        // Chuyển hướng người dùng về trang login.jsp
        response.sendRedirect("index");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chỉ cần gọi lại phương thức doGet trong trường hợp POST
        doGet(request, response);
    }
}

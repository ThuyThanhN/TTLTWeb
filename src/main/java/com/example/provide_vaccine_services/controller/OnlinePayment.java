package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "OnlinePayment", value = "/onlinePayment")
public class OnlinePayment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type for response as HTML
        response.setContentType("text/html");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        // Log user accessing the online payment page via GET
        try {
            logDao.insertLog("INFO", "User accessed online payment page (GET request)", "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace(); // Log error but do not interrupt response flow
        }

        // Forward request to onlinePayment.jsp page
        request.getRequestDispatcher("onlinePayment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation yet for POST
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        // Log POST access (placeholder for future payment processing)
        try {
            logDao.insertLog("INFO", "User accessed online payment (POST request) - not implemented", "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // You may add processing code here in future
    }
}

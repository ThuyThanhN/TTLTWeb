package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LogTestServlet", value = "/test-log")
public class LogTestServlet extends HttpServlet {

    private LogDao logDao = new LogDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIp = request.getRemoteAddr();
        String testUser = "testUser";

        try {
            logDao.insertLog("INFO", "This is a test log message", testUser, userIp);
            response.getWriter().write("Log inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Failed to insert log: " + e.getMessage());
        }
    }
}

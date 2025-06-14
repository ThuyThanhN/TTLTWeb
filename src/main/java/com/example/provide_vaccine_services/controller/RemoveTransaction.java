package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveTransaction", value = "/admin/removeTransaction")
public class RemoveTransaction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int transactionId = Integer.parseInt(request.getParameter("id"));
            System.out.println("transaction Id: " + transactionId);

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.deleteById(transactionId);

            response.getWriter().write("{\"status\": \"success\", \"message\": \"Xóa thành công\"}");
        } catch (Exception e) {
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Xóa thất bại!\"}");
        }
    }
}


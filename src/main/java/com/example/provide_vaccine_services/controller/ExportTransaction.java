package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExportTransaction", value = "/admin/exportTransaction")
public class ExportTransaction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        TransactionDAO transactionDAO = new TransactionDAO();
        List<Map<String, Object>> transactionData = transactionDAO.export();

        // Log
        for(Map<String, Object> transaction : transactionData) {
            System.out.println(transaction.get("id"));
            System.out.println(transaction.get("vaccineName"));
            System.out.println(transaction.get("type"));
            System.out.println(transaction.get("quantity"));
            System.out.println(transaction.get("date"));
            System.out.println(transaction.get("expiry_date"));
            System.out.println(transaction.get("user_id"));
        }

        // jSON
        String json = new Gson().toJson(transactionData);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

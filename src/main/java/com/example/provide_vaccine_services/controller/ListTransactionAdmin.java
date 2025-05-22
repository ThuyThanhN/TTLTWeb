package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListTransactionAdmin", value = "/admin/table-data-transaction")
public class ListTransactionAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách vắc xin
        TransactionDAO transactionDAO = new TransactionDAO();
        VaccineDao vaccineDAO = new VaccineDao();
        List<Vaccines> vaccines = vaccineDAO.getAll();
        Map<Integer, String> map = new HashMap<>();

        for (Vaccines v : vaccines) {
            map.put(v.getId(), v.getName());
        }
        List<Transaction> transactions = transactionDAO.getAllTransaction();
        request.setAttribute("transactions", transactions);
        request.setAttribute("vaccines", map);
        request.getRequestDispatcher("table-data-transaction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


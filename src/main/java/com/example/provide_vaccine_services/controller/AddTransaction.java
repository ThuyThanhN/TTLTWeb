package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.CenterDao;
import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Centers;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Users;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "AddTransaction", value = "/admin/addTransaction")

public class AddTransaction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String vaccine = request.getParameter("vaccine");
        String quantity = request.getParameter("quantityVaccine");
        String type = request.getParameter("type");
        String centerName = request.getParameter("center");
        LocalDateTime createdAt = LocalDateTime.now();

        // center
        CenterDao centerDao = new CenterDao();
        Centers center = centerDao.getById(Integer.parseInt(centerName));

        // vaccine
        VaccineDao vaccineDao = new VaccineDao();
        Vaccines vaccines = vaccineDao.getVaccineById(Integer.parseInt(vaccine));

        // parse
        int centerId = Integer.parseInt(centerName);
        int vaccineId = Integer.parseInt(vaccine);
        int quantityVaccine = Integer.parseInt(quantity);
        Users user = (Users) request.getSession().getAttribute("user");

        Transaction transaction = new Transaction(centerId, vaccineId, type, quantityVaccine, user);
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.insert(transaction);

    }

}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Users;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(name = "UpdateTransaction", value = "/admin/updateTransaction")
public class UpdateTransaction extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String quantityStr = request.getParameter("quantity");
        String expiryDateStr = request.getParameter("expiry_date");

        System.out.println(id);
        System.out.println(type);
        System.out.println(quantityStr);
        System.out.println(expiryDateStr);

        // Chuyển đổi tham số thành các kiểu dữ liệu phù hợp
        int idTransaction = Integer.parseInt(id);
        int quantity = Integer.parseInt(quantityStr);
        LocalDateTime expiryDate = LocalDate.parse(expiryDateStr).atStartOfDay();
        Users user = (Users) request.getSession().getAttribute("user");

        TransactionDAO transactionDAO = new TransactionDAO();


        // Tạo đối tượng Transaction
        Transaction transaction = transactionDAO.getTransactionById(idTransaction);

        // lấy dữ liệu cũ
        String oldType = transaction.getType();
        int oldQuantity = transaction.getQuantity();


        // cập nhật dữ liệu mới
        transaction.setType(type);
        transaction.setQuantity(quantity);
        transaction.setExpiry_date(expiryDate);
        transaction.setUser(user);
        int result = transactionDAO.update(transaction);

        // cập nhật vào kho
        VaccineDao vaccineDao = new VaccineDao();

        oldQuantity = ( oldType.equals("Nhập")) ? -oldQuantity : oldQuantity;
        int delta = ( transaction.getType().equals("Nhập")) ?  quantity : -quantity ;

        try {
            vaccineDao.updateQuantity(transaction.getVaccineId(),oldQuantity + delta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        if (result > 0) {
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Transaction updated successfully.\"}");
        } else {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Update failed.\"}");
        }


    }
}


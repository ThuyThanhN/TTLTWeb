package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ListAddTransaction", value = "/admin/form-add-transaction")
public class ListAddTransaction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();
        HttpSession session = request.getSession();
        String userEmail = "anonymous";
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            userEmail = user.getEmail();
        }

        try {
            // Lấy danh sách nhà cung cấp, nhóm tuổi, nhóm bệnh, trung tâm
            AgeGroupDao ageDao = new AgeGroupDao();
            DisaseGroupDao disaseDao = new DisaseGroupDao();
            VaccineDao vaccineDao = new VaccineDao();
            CenterDao centerDao = new CenterDao();

            List<Vaccines> vaccines = vaccineDao.getAll();
            List<AgeGroups> ages = ageDao.getAgeGroups();
            List<DisaseGroups> disases = disaseDao.getDisaseGroups();
            List<Centers> centers = centerDao.getAll();

            // Đặt dữ liệu vào request để chuyển tiếp đến JSP
            request.setAttribute("vaccines", vaccines);
            request.setAttribute("centers", centers);
            request.setAttribute("ages", ages);
            request.setAttribute("disases", disases);

            // Ghi log truy cập form thêm transaction
            logDao.insertLog("INFO", "Accessed form to add transaction", userEmail, userIp);

            request.getRequestDispatcher("form-add-transaction.jsp").forward(request, response);
        } catch (SQLException e) {
            try {
                logDao.insertLog("ERROR", "Failed to load form-add-transaction data: " + e.getMessage(), userEmail, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải form thêm giao dịch");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        try {
            // Lấy dữ liệu từ form
            String vaccine = request.getParameter("vaccine");
            String quantity = request.getParameter("quantityVaccine");
            String type = request.getParameter("type");
            String expiry_date = request.getParameter("expiry_date");

            LocalDate expiryDate = LocalDate.parse(expiry_date); // yyyy-MM-dd
            LocalDateTime expiryDateTime = expiryDate.atStartOfDay();

            int vaccineId = Integer.parseInt(vaccine);
            int quantityVaccine = Integer.parseInt(quantity);

            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            String userEmail = user != null ? user.getEmail() : "anonymous";

            // Tạo transaction mới
            Transaction t = new Transaction(vaccineId, type, quantityVaccine, expiryDateTime, user);
            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.insert(t);

            // Cập nhật kho hàng
            VaccineDao vaccineDao = new VaccineDao();
            ProductStockDAO productStockDAO = new ProductStockDAO();
            ProductStock existingStock = productStockDAO.findByVaccineId(t.getVaccineId());

            int delta = t.getType().equals("1") ? t.getQuantity() : -t.getQuantity();

            if (existingStock != null) {
                productStockDAO.updateQuantity(t.getVaccineId(), delta);
                logDao.insertLog("INFO", "Updated product stock quantity for vaccine ID " + t.getVaccineId() + " by " + delta, userEmail, userIp);
            } else {
                ProductStock newStock = new ProductStock(
                        t.getVaccineId(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getName(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getPrice() * t.getQuantity(),
                        0,
                        expiryDateTime
                );
                productStockDAO.insert(newStock, delta);
                logDao.insertLog("INFO", "Inserted new product stock for vaccine ID " + t.getVaccineId() + " with quantity " + delta, userEmail, userIp);
            }

            // Ghi log thêm giao dịch thành công
            logDao.insertLog("INFO", "Inserted new transaction for vaccine ID " + vaccineId + " with quantity " + quantityVaccine + " and type " + type, userEmail, userIp);

            // Chuyển hướng về trang dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");

        } catch (Exception e) {
            try {
                logDao.insertLog("ERROR", "Error processing transaction add: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý thêm giao dịch");
        }
    }
}

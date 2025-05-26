package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ListAddVaccine", value = "/admin/form-add-transaction")
public class ListAddTransaction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách nhà cung cấp
        AgeGroupDao ageDao = new AgeGroupDao();
        DisaseGroupDao disaseDao = new DisaseGroupDao();
        VaccineDao vaccineDao = new VaccineDao();
        CenterDao centerDao = new CenterDao();

        List<Vaccines> vaccines = vaccineDao.getAll();
        List<AgeGroups> ages = ageDao.getAgeGroups();
        List<DisaseGroups> disases = disaseDao.getDisaseGroups();
        List<Centers> centers = centerDao.getAll();

        request.setAttribute("vaccines", vaccines);
        request.setAttribute("centers", centers);
        request.setAttribute("ages", ages);
        request.setAttribute("disases", disases);
        request.getRequestDispatcher("form-add-transaction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String vaccine = request.getParameter("vaccine");
        String quantity = request.getParameter("quantityVaccine");
        String type = request.getParameter("type");
        String expiry_date = request.getParameter("expiry_date");
        LocalDate expiryDate = LocalDate.parse(expiry_date); // yyyy-MM-dd
        LocalDateTime expiryDateTime = expiryDate.atStartOfDay(); // chuyển thành LocalDateTime


        // parse
        int vaccineId = Integer.parseInt(vaccine);
        int quantityVaccine = Integer.parseInt(quantity);
        Users user = (Users) request.getSession().getAttribute("user");

        // thêm transaction
        Transaction t = new Transaction(vaccineId, type, quantityVaccine, expiryDateTime, user);
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.insert(t);

        // cộng vào productStock
        VaccineDao vaccineDao = new VaccineDao();
        ProductStockDAO productStockDAO = new ProductStockDAO();
        ProductStock existingStock = null;
        try {
            existingStock = productStockDAO.findByVaccineId(t.getVaccineId());
            int delta = (t.getType().equals("1")) ? t.getQuantity() : -t.getQuantity();
            System.out.println(t.getType());

            if (existingStock != null) {
                System.out.println("Existing stock is not null");
                productStockDAO.updateQuantity(t.getVaccineId(), delta);
            } else {
                System.out.println("Existing stock is null");
                // nếu chưa có, tạo mới. Cần truyền thêm productName và expired từ form hoặc truy DB vaccine
                ProductStock newStock = new ProductStock(
                        t.getVaccineId(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getName(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getPrice() * t.getQuantity(),
                        0,               // loss ban đầu = 0
                        expiryDateTime // hết hạn sau 6 tháng
                );
                productStockDAO.insert(newStock, delta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }

}

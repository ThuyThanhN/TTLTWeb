package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.ProductStockDAO;
import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.ProductStock;
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

@WebServlet(name = "ListWarehouseAdmin", value = "/admin/table-data-warehouse")
public class ListWarehouseAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách vắc xin
        ProductStockDAO productStockDAO = new ProductStockDAO();
        List<ProductStock> productStocks = productStockDAO.getAll();
        Map<Integer, Vaccines> map = new HashMap<>();
        VaccineDao vaccineDao = new VaccineDao();
        for (ProductStock productStock : productStocks) {
            map.put(productStock.getVaccineId(), vaccineDao.getVaccineById(productStock.getVaccineId()));
        }

        request.setAttribute("vaccinesMap", map);
        request.setAttribute("productStocks", productStocks);
        request.getRequestDispatcher("table-data-warehouse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


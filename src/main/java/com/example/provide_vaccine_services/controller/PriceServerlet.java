// File: VaccineController.java
package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "PriceServerlet", value = "/price")
public class PriceServerlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Khởi tạo DAO
        VaccineDao vaccineDao = new VaccineDao();

        // Lấy tham số sort từ URL
        String sort = request.getParameter("sort");
        System.out.println("Sort parameter received: " + sort);

        // Lấy danh sách vaccine đã sắp xếp từ DAO
        List<Vaccines> vaccinesList = vaccineDao.getAlltable(sort);
        System.out.println("Vaccines list size: " + vaccinesList.size());
        for (Vaccines vaccine : vaccinesList) {
            System.out.println(vaccine.getName() + " - " + vaccine.getPrice());
        }
        // Gửi dữ liệu sang JSP
        request.setAttribute("vaccines", vaccinesList);
        request.getRequestDispatcher("price.jsp").forward(request, response);
    }
}
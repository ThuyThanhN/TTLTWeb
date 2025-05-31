// File: VaccineController.java
package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PriceServerlet", value = "/price")
public class PriceServerlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập mã hóa ký tự UTF-8 cho request
        request.setCharacterEncoding("UTF-8");

        // Khởi tạo các DAO cần thiết
        VaccineDao vaccineDao = new VaccineDao();
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        // Lấy tham số sort từ URL (nếu có)
        String sort = request.getParameter("sort");
        try {
            // Ghi log nhận tham số sort
            logDao.insertLog("INFO", "Received sort parameter: " + sort, "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lấy danh sách vaccine đã sắp xếp dựa trên tham số sort
        List<Vaccines> vaccinesList = vaccineDao.getAlltable(sort);

        try {
            // Ghi log số lượng vaccine lấy được
            logDao.insertLog("INFO", "Fetched vaccines list of size: " + vaccinesList.size(), "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đặt danh sách vaccine vào request attribute để truyền sang JSP
        request.setAttribute("vaccines", vaccinesList);

        // Chuyển tiếp request tới trang price.jsp để hiển thị
        request.getRequestDispatcher("price.jsp").forward(request, response);
    }
}

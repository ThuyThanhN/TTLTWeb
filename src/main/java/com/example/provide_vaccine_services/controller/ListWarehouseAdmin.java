package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.ProductStockDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.ProductStock;
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
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách vắc xin trong kho
            ProductStockDAO productStockDAO = new ProductStockDAO();
            List<ProductStock> productStocks = productStockDAO.getAll();
            VaccineDao vaccineDao = new VaccineDao();

            // Tạo map chứa vaccineId và đối tượng Vaccine tương ứng
            Map<Integer, Vaccines> map = new HashMap<>();
            for (ProductStock productStock : productStocks) {
                map.put(productStock.getVaccineId(), vaccineDao.getVaccineById(productStock.getVaccineId()));
            }

            // Gán dữ liệu vào request attribute để chuyển sang JSP
            request.setAttribute("vaccinesMap", map);
            request.setAttribute("productStocks", productStocks);

            // Ghi log truy cập bảng kho hàng thành công
            logDao.insertLog("INFO", "Accessed warehouse data table", "anonymous", userIp);

            // Chuyển tiếp sang trang hiển thị dữ liệu kho hàng
            request.getRequestDispatcher("table-data-warehouse.jsp").forward(request, response);
        } catch (Exception e) {
            // Ghi log lỗi nếu có ngoại lệ
            try {
                logDao.insertLog("ERROR", "Error accessing warehouse data: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi truy cập dữ liệu kho hàng");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong lớp này
    }
}

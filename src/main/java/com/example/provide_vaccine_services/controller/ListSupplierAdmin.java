package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListSupplierAdmin", value = "/admin/table-data-supplier")
public class ListSupplierAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách nhà cung cấp
            SupplierDao supplierDao = new SupplierDao();
            List<Suppliers> suppliers = supplierDao.getAll();

            request.setAttribute("suppliers", suppliers);

            // Ghi log truy cập danh sách nhà cung cấp admin
            logDao.insertLog("INFO", "Accessed supplier list for admin", "anonymous", userIp);

            // Chuyển tiếp đến trang hiển thị danh sách nhà cung cấp
            request.getRequestDispatcher("table-data-supplier.jsp").forward(request, response);

        } catch (Exception e) {
            // Ghi log lỗi khi tải danh sách nhà cung cấp
            try {
                logDao.insertLog("ERROR", "Error loading supplier list for admin: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách nhà cung cấp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.DisaseGroupDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListAddVaccine", value = "/admin/form-add-vacxin")
public class ListAddVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userIp = request.getRemoteAddr();

        LogDao logDao = new LogDao();

        try {
            // Lấy danh sách nhà cung cấp, nhóm tuổi, nhóm bệnh
            SupplierDao supplierDao = new SupplierDao();
            AgeGroupDao ageDao = new AgeGroupDao();
            DisaseGroupDao disaseDao = new DisaseGroupDao();

            List<Suppliers> suppliers = supplierDao.getAll();
            List<AgeGroups> ages = ageDao.getAgeGroups();
            List<DisaseGroups> disases = disaseDao.getDisaseGroups();

            // Đặt dữ liệu vào request để chuyển tiếp tới JSP
            request.setAttribute("suppliers", suppliers);
            request.setAttribute("ages", ages);
            request.setAttribute("disases", disases);

            // Ghi log việc truy cập form thêm vaccine
            logDao.insertLog("INFO", "Accessed form to add vaccine", "anonymous", userIp);

            // Chuyển tiếp đến trang form-add-vacxin.jsp
            request.getRequestDispatcher("form-add-vacxin.jsp").forward(request, response);

        } catch (SQLException e) {
            // Ghi log lỗi khi lấy dữ liệu
            try {
                logDao.insertLog("ERROR", "Failed to load data for add vaccine form: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải form thêm vaccine");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong controller này
    }
}

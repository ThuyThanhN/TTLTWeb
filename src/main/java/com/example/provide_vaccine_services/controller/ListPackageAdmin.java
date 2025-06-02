package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListPackage", value = "/admin/table-data-vax-package")
public class ListPackageAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Khởi tạo các DAO để lấy dữ liệu
            VaccineDao vaccineDao = new VaccineDao();
            VaccinePackageDao vpDao = new VaccinePackageDao();
            AgeGroupDao ageDao = new AgeGroupDao();

            // Lấy danh sách các vaccine, gói vaccine và nhóm tuổi
            List<Vaccines> vaccines = vaccineDao.getAll();
            List<VaccinePackages> vps = vpDao.getAll();
            List<AgeGroups> ages = ageDao.getAgeGroups();

            // Đặt dữ liệu vào request attributes để chuyển sang JSP
            request.setAttribute("vaccines", vaccines);
            request.setAttribute("vps", vps);
            request.setAttribute("ages", ages);
            request.setAttribute("packageList", vpDao.getPackage());

            // Ghi log truy cập thành công danh sách gói vaccine
            logDao.insertLog("INFO", "Admin accessed vaccine package list", "anonymous", userIp);

            // Chuyển tiếp đến trang JSP hiển thị danh sách gói vaccine
            request.getRequestDispatcher("table-data-vax-package.jsp").forward(request, response);

        } catch (SQLException e) {
            // Ghi log lỗi khi lấy dữ liệu danh sách gói vaccine
            try {
                logDao.insertLog("ERROR", "Error loading vaccine package list: " + e.getMessage(), "anonymous", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách gói vaccine");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

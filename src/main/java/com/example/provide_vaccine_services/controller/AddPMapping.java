package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.PackageAgeDao;
import com.example.provide_vaccine_services.dao.VaccinePMappingDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.model.PackageAges;
import com.example.provide_vaccine_services.dao.model.VaccinePMappings;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddPMapping", value = "/admin/addPMapping")
public class AddPMapping extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý GET
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        String packageName = request.getParameter("package-name");
        String totalPrice = request.getParameter("totalPrice");
        String description = request.getParameter("description-name");

        String[] idVaccines = request.getParameterValues("vaccineId");
        String[] dosages = request.getParameterValues("dosage");
        int idAge = Integer.parseInt(request.getParameter("age-select"));

        totalPrice = totalPrice.replaceAll("[^0-9]", "");
        float totalPriceFloat = Float.parseFloat(totalPrice);

        VaccinePackageDao vpDao = new VaccinePackageDao();
        VaccinePMappingDao vpmDao = new VaccinePMappingDao();
        PackageAgeDao paDao = new PackageAgeDao();

        try {
            // Thêm gói tiêm
            int packageId = vpDao.insert(new VaccinePackages(packageName, description, totalPriceFloat));
            if (packageId <= 0) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Không thể thêm gói tiêm.\"}");
                logDao.insertLog("ERROR", "Thêm gói tiêm thất bại: " + packageName, "-", userIp);
                return;
            }

            // Thêm mapping vaccine - gói tiêm
            if (idVaccines != null && dosages != null && idVaccines.length == dosages.length) {
                for (int i = 0; i < idVaccines.length; i++) {
                    int vaccineId = Integer.parseInt(idVaccines[i]);
                    int dosageNum = Integer.parseInt(dosages[i]);
                    VaccinePMappings vpMapping = new VaccinePMappings(vaccineId, packageId, dosageNum);
                    vpmDao.insert(vpMapping);
                }
            }

            // Thêm mapping nhóm tuổi - gói tiêm
            PackageAges pa = new PackageAges(idAge, packageId);
            paDao.insert(pa);

            response.getWriter().write("{\"status\":\"success\", \"id\":" + packageId + "}");
            logDao.insertLog("INFO", "Thêm gói tiêm thành công, id: " + packageId + ", tên: " + packageName, "-", userIp);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi trong quá trình thêm gói tiêm: " + e.getMessage() + "\"}");
            try {
                logDao.insertLog("ERROR", "Lỗi thêm gói tiêm: " + e.getMessage(), "-", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

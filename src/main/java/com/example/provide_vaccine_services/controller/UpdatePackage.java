package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "UpdatePackage", value = "/admin/updatePackage")
public class UpdatePackage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            int packageId = Integer.parseInt(request.getParameter("id"));
            int ageId = Integer.parseInt(request.getParameter("ageId"));
            String packageName = request.getParameter("package-name");
            String totalPrice = request.getParameter("totalPrice");
            String description = request.getParameter("description-name");

            totalPrice = totalPrice.replaceAll("[^0-9]", "");
            float totalPriceFloat = Float.parseFloat(totalPrice);

            String[] vaccineIdArray = request.getParameterValues("vaccineId");
            String[] dosageArray = request.getParameterValues("dosage");

            List<Integer> vaccineIds = new ArrayList<>();
            List<Integer> dosages = new ArrayList<>();

            if (vaccineIdArray != null && dosageArray != null) {
                vaccineIds = Arrays.stream(vaccineIdArray)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                dosages = Arrays.stream(dosageArray)
                        .map(d -> {
                            try {
                                return Integer.parseInt(d);
                            } catch (NumberFormatException e) {
                                return 1;
                            }
                        }).collect(Collectors.toList());
            }

            VaccinePackageDao dao = new VaccinePackageDao();
            int result = dao.update(packageId, packageName, vaccineIds, dosages, ageId, totalPriceFloat, description);

            if (result > 0) {
                try {
                    logDao.insertLog("INFO", "Vaccine package updated successfully: ID=" + packageId + ", Name=" + packageName, "admin", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Cập nhật thành công\"}");
            } else {
                try {
                    logDao.insertLog("ERROR", "Failed to update vaccine package: ID=" + packageId + ", Name=" + packageName, "admin", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.getWriter().write("{\"status\": \"fail\", \"message\": \"Cập nhật thất bại\"}");
            }

        } catch (Exception e) {
            try {
                logDao.insertLog("ERROR", "Exception in UpdatePackage doPost: " + e.getMessage(), "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Có lỗi xảy ra khi cập nhật\"}");
        }
    }
}

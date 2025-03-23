package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "UpdatePackage", value = "/admin/updatePackage")
public class UpdatePackage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Xử lý cập nhật gói vắc xin
        int packageId = Integer.parseInt(request.getParameter("id"));
        int ageId = Integer.parseInt(request.getParameter("ageId"));
        String packageName = request.getParameter("package-name");
        String totalPrice = request.getParameter("totalPrice");
        String description = request.getParameter("description-name");
        totalPrice = totalPrice.replaceAll("[^0-9]", ""); // Loại bỏ các ký tự không phải số
        float totalPriceFloat = Float.parseFloat(totalPrice);

        String[] vaccineIdArray = request.getParameterValues("vaccineId");  // Vaccine IDs
        String[] dosageArray = request.getParameterValues("dosage");  // Dosages

        // Chuyển đổi mảng vaccineId và dosage sang danh sách Integer
        List<Integer> vaccineIds = Arrays.stream(vaccineIdArray)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> dosages = Arrays.stream(dosageArray)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        VaccinePackageDao dao = new VaccinePackageDao();
        int result = dao.update(packageId, packageName, vaccineIds, dosages, ageId, totalPriceFloat, description);

        if (result > 0) {
            response.getWriter().write("Cap nhat thanh cong");
        } else {
            response.getWriter().write("Cap nhat that bai");
        }

        response.sendRedirect("table-data-vax-package");
    }
}


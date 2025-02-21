package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.PackageAgeDao;
import com.example.provide_vaccine_services.dao.VaccinePMappingDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.model.PackageAges;
import com.example.provide_vaccine_services.dao.model.VaccinePMappings;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddPMapping", value = "/addPMapping")
public class AddPMapping extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String packageName = request.getParameter("package-name");
        String totalPrice = request.getParameter("totalPrice");
        String description = request.getParameter("description-name");

        totalPrice = totalPrice.replaceAll("[^0-9]", ""); // Loại bỏ các ký tự không phải số
        float totalPriceFloat = Float.parseFloat(totalPrice);

        String[] idVaccines = request.getParameterValues("vaccineId");
        String[] dosages = request.getParameterValues("dosage");

        int idAge = Integer.parseInt(request.getParameter("age-select"));

        VaccinePackageDao vpDao = new VaccinePackageDao();
        VaccinePMappingDao vpmDao = new VaccinePMappingDao();
        PackageAgeDao paDao = new PackageAgeDao();

        int packageId = vpDao.insert(new VaccinePackages(packageName,description,totalPriceFloat));
        if (idVaccines != null) {
            for (int i = 0; i < idVaccines.length; i++) {
                int vaccineId = Integer.parseInt(idVaccines[i]);
                int dosageNum = Integer.parseInt(dosages[i]);
                VaccinePMappings vpMapping = new VaccinePMappings(vaccineId, packageId, dosageNum);
                vpmDao.insert(vpMapping);
            }
        }

        PackageAges pa = new PackageAges(idAge, packageId);
        paDao.insert(pa);

        response.sendRedirect("table-data-vax-package");
    }
}




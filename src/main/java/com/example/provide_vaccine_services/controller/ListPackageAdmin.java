package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
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
import java.util.List;

@WebServlet(name = "ListPackage", value = "/table-data-vax-package")
public class ListPackageAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        VaccineDao vaccineDao = new VaccineDao();
        VaccinePackageDao vpDao = new VaccinePackageDao();
        AgeGroupDao ageDao = new AgeGroupDao();

        List<Vaccines> vaccines = vaccineDao.getAll();
        List<VaccinePackages> vps = vpDao.getAll();
        List<AgeGroups> ages = ageDao.getAgeGroups();

        request.setAttribute("vaccines", vaccines);
        request.setAttribute("vps", vps);
        request.setAttribute("ages", ages);
        request.setAttribute("packageList", vpDao.getPackage());

        request.getRequestDispatcher("table-data-vax-package.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


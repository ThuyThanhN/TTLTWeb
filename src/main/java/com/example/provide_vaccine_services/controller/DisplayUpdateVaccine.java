package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DisplayUpdateVaccine", value = "/admin/displayVaccine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 50,  // 50 KB
        maxFileSize = 1024 * 500,       // 500 KB
        maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class DisplayUpdateVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int idVaccine = Integer.parseInt(request.getParameter("id")); // id vac xin

        SupplierDao supplierDao = new SupplierDao();
        List<Suppliers> suppliers = supplierDao.getAll();
        Suppliers supplier = supplierDao.getById(idVaccine);

        AgeGroupDao ageDao = new AgeGroupDao();
        List<AgeGroups> ages = ageDao.getAgeGroups();
        AgeGroups age = ageDao.getAgeById(idVaccine);

        DisaseGroupDao disaseDao = new DisaseGroupDao();
        List<DisaseGroups> disases = disaseDao.getDisaseGroups();
        DisaseGroups disase = disaseDao.getDisaseById(idVaccine);

        VaccineDao vaccineDao = new VaccineDao();
        Vaccines vaccine = vaccineDao.getVaccineById(idVaccine);

        VacccineDetailDao detailDao = new VacccineDetailDao();
        VacccineDetails vdetail = detailDao.getVaccineDetailsById(idVaccine);

        VaccineContentDao contentDao = new VaccineContentDao();
        VaccineContents vcontent = contentDao.getVaccineContentsById(idVaccine);

        request.setAttribute("v", vaccine);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("supplier", supplier);
        request.setAttribute("ages", ages);
        request.setAttribute("age", age);
        request.setAttribute("disases", disases);
        request.setAttribute("disase", disase);
        request.setAttribute("vdetail", vdetail);
        request.setAttribute("vcontent", vcontent);

        request.getRequestDispatcher("update-vacxin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
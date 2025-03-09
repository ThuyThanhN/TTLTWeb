package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.DisaseGroupDao;
import com.example.provide_vaccine_services.dao.SupplierDao;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListAddVaccine", value = "/admin/form-add-vacxin")
public class ListAddVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách nhà cung cấp
        SupplierDao supplierDao = new SupplierDao();
        AgeGroupDao ageDao = new AgeGroupDao();
        DisaseGroupDao disaseDao = new DisaseGroupDao();

        List<Suppliers> suppliers = supplierDao.getAll();
        List<AgeGroups> ages = ageDao.getAgeGroups();
        List<DisaseGroups> disases = disaseDao.getDisaseGroups();

        request.setAttribute("suppliers", suppliers);
        request.setAttribute("ages", ages);
        request.setAttribute("disases", disases);
        request.getRequestDispatcher("form-add-vacxin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


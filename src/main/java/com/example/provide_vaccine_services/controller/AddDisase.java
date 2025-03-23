package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.AgeGroupDao;
import com.example.provide_vaccine_services.dao.DisaseGroupDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddDisase", value = "/admin/addDisase")
public class AddDisase extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        DisaseGroupDao dao = new DisaseGroupDao();
        DisaseGroups disasse = new DisaseGroups(name);
        dao.insert(disasse);

        response.sendRedirect("form-add-vacxin");
    }
}


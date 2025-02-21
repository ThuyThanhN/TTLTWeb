package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "UpdateVaccine", value = "/updateVaccine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 50,  // 50 KB
        maxFileSize = 1024 * 500,       // 500 KB
        maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class UpdateVaccine extends HttpServlet {

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

        request.getRequestDispatcher("update-add-vacxin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idv = request.getParameter("idVaccine");
        int id = Integer.parseInt(idv);

        // Vaccines update
        String name = request.getParameter("vaccineName");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");
        String status = request.getParameter("status");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        String prevention = request.getParameter("prevention");
        LocalDateTime createdAt = LocalDateTime.now();
        int quantityI = Integer.parseInt(quantity);
        float priceF = Float.parseFloat(price);
        int supplierId = Integer.parseInt(request.getParameter("supplierName"));
        String statusText = (Integer.parseInt(status) == 1) ? "Còn hàng" : "Hết hàng";

        Vaccines vaccines = new Vaccines(id, supplierId, name, description, quantityI, priceF, image,  statusText, createdAt, prevention);
        VaccineDao vaccineDao = new VaccineDao();
        vaccineDao.updateVaccine(vaccines);

//       AgeGroups + DisaseGroups update
        int age = Integer.parseInt(request.getParameter("age-name"));
        int disase = Integer.parseInt(request.getParameter("disase-name"));
        VaccineTypes vt = new VaccineTypes(id, age, disase);
        VaccineTypeDao vtDao = new VaccineTypeDao();
        vtDao.updateType(vt);

//      VaccineDetails update
        String target = request.getParameter("editor-dt");
        String immunization = request.getParameter("editor-pdt");
        String  adverseReactions = request.getParameter("editor-pu");

        VacccineDetailDao vdDao = new VacccineDetailDao();
        VacccineDetails vd = new VacccineDetails(id, target, immunization, adverseReactions);
        vdDao.updateVaccineDetail(vd);

//      VaccineContents update
        String origin = request.getParameter("editor-ng");
        String administrationRoute = request.getParameter("editor-dt");
        String contraindications = request.getParameter("editor-ccd");
        String precaution = request.getParameter("editor-tt");
        String drugInteractions = request.getParameter("editor-ttt");
        String sideEffects = request.getParameter("editor-tdp");

        VaccineContentDao vcDao = new VaccineContentDao();
        int idDetail = vdDao.getIdDetail(id);
        VaccineContents vc = new VaccineContents(idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects);
        vcDao.updateVaccineContent(vc);

        response.sendRedirect("table-data-vacxin");
    }
}
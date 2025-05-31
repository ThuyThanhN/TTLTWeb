package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateVaccine", value = "/admin/updateVaccine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 50,
        maxFileSize = 1024 * 500,
        maxRequestSize = 1024 * 1024 * 10
)
public class UpdateVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation needed here as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("idVaccine"));

            String uploadPath = "D:" + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();
            String imagePath = request.getParameter("existingImage");

            if (fileName != null && !fileName.isEmpty()) {
                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);
                imagePath = "/uploads/" + fileName;
            }

            String name = request.getParameter("vaccineName");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            float price = Float.parseFloat(request.getParameter("price"));
            String statusText = (Integer.parseInt(request.getParameter("status")) == 1) ? "Còn hàng" : "Hết hàng";
            String description = request.getParameter("description");
            String prevention = request.getParameter("prevention");
            int supplierId = Integer.parseInt(request.getParameter("supplierName"));
            LocalDateTime createdAt = LocalDateTime.now();

            Vaccines vaccines = new Vaccines(id, supplierId, name, description, quantity, price, imagePath, statusText, createdAt, prevention);
            VaccineDao vaccineDao = new VaccineDao();
            boolean vaccineUpdated = vaccineDao.updateVaccine(vaccines);

            int age = Integer.parseInt(request.getParameter("age-name"));
            int disease = Integer.parseInt(request.getParameter("disase-name"));
            VaccineTypes vt = new VaccineTypes(id, age, disease);
            VaccineTypeDao vtDao = new VaccineTypeDao();
            boolean typeUpdated = vtDao.updateType(vt);

            String target = request.getParameter("editor-dt");
            String immunization = request.getParameter("editor-pdt");
            String adverseReactions = request.getParameter("editor-pu");

            VacccineDetailDao vdDao = new VacccineDetailDao();
            VacccineDetails vd = new VacccineDetails(id, target, immunization, adverseReactions);
            boolean detailUpdated = vdDao.updateVaccineDetail(vd);

            String origin = request.getParameter("editor-ng");
            String administrationRoute = request.getParameter("editor-dt");
            String contraindications = request.getParameter("editor-ccd");
            String precaution = request.getParameter("editor-tt");
            String drugInteractions = request.getParameter("editor-ttt");
            String sideEffects = request.getParameter("editor-tdp");

            VaccineContentDao vcDao = new VaccineContentDao();
            int idDetail = vdDao.getIdDetail(id);
            VaccineContents vc = new VaccineContents(idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects);
            boolean contentUpdated = vcDao.updateVaccineContent(vc);

            Map<String, Object> jsonResponse = new HashMap<>();
            if (vaccineUpdated && typeUpdated && detailUpdated && contentUpdated) {
                try {
                    logDao.insertLog("INFO", "Vaccine update successful for ID: " + id + ", Name: " + name, "admin", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jsonResponse.put("status", "success");
                jsonResponse.put("id", id);
                jsonResponse.put("name", name);
                jsonResponse.put("stockQuantity", quantity);
                jsonResponse.put("price", price);
                jsonResponse.put("statusText", statusText);
                jsonResponse.put("imageUrl", imagePath);
            } else {
                try {
                    logDao.insertLog("ERROR", "Vaccine update failed for ID: " + id + ", Name: " + name, "admin", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Loi khi cap nhat vac xin");
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new com.google.gson.Gson().toJson(jsonResponse));

        } catch (Exception e) {
            try {
                logDao.insertLog("ERROR", "Exception in UpdateVaccine doPost: " + e.getMessage(), "admin", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi cập nhật vaccine.");
        }
    }
}

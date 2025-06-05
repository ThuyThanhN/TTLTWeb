package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddVaccine", value = "/admin/addVaccine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 100,  // 100 KB
        maxFileSize = 1024 * 500,       // 500 KB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class AddVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý GET trong servlet này
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            String uploadPath = "/opt/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();
            String filePath = uploadPath + File.separator + fileName;

            filePart.write(filePath);

            String imagePath = "/uploads/" + fileName;

            String name = request.getParameter("vaccineName");
            String quantity = request.getParameter("quantityVaccine");
            String price = request.getParameter("price");
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String prevention = request.getParameter("prevention");

            LocalDateTime createdAt = LocalDateTime.now();

            int quantityI = Integer.parseInt(quantity);
            float priceF = Float.parseFloat(price);
            int supplierId = Integer.parseInt(request.getParameter("supplier"));
            String statusText = (Integer.parseInt(status) == 1) ? "Còn hàng" : "Hết hàng";

            String target = request.getParameter("editor-dt");
            String immunization = request.getParameter("editor-pdt");
            String adverseReactions = request.getParameter("editor-pu");

            String origin = request.getParameter("editor-ng");
            String administrationRoute = request.getParameter("editor-dt");
            String contraindications = request.getParameter("editor-ccd");
            String precaution = request.getParameter("editor-tt");
            String drugInteractions = request.getParameter("editor-ttt");
            String sideEffects = request.getParameter("editor-tdp");

            int idAgeGroup = Integer.parseInt(request.getParameter("age-name"));
            int idDisaseGroup = Integer.parseInt(request.getParameter("disage-name"));

            VaccineDao vaccineDao = new VaccineDao();
            Vaccines vaccine = new Vaccines(supplierId, name, description, quantityI, priceF, imagePath, statusText, createdAt, prevention);
            int idVaccine = vaccineDao.insert(vaccine);

            VacccineDetailDao vdDao = new VacccineDetailDao();
            VacccineDetails vd = new VacccineDetails(idVaccine, target, immunization, adverseReactions);
            int idDetail = vdDao.insert(vd);

            VaccineContentDao vcDao = new VaccineContentDao();
            VaccineContents vc = new VaccineContents(idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects);
            vcDao.insert(vc);

            VaccineTypes vt = new VaccineTypes(idVaccine, idAgeGroup, idDisaseGroup);
            VaccineTypeDao vtDao = new VaccineTypeDao();
            vtDao.insert(vt);

            if (idVaccine > 0) {
                logDao.insertLog("INFO", "Thêm vắc xin thành công với id: " + idVaccine, name, userIp);
                response.getWriter().write("{\"status\":\"success\", \"id\":" + idVaccine + "}");
            } else {
                logDao.insertLog("ERROR", "Thêm vắc xin thất bại", name, userIp);
                response.getWriter().write("{\"status\":\"error\"}");
            }

        } catch (Exception e) {
            try {
                logDao.insertLog("ERROR", "Lỗi khi thêm vắc xin: " + e.getMessage(), "Unknown", userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Có lỗi xảy ra\"}");
        }
    }
}

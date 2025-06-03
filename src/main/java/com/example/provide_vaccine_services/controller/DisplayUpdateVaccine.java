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
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        int idVaccine = Integer.parseInt(request.getParameter("id")); // Lấy id vắc xin từ tham số request

        try {
            logDao.insertLog("INFO", "Bắt đầu lấy dữ liệu cho vắc xin id: " + idVaccine, "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SupplierDao supplierDao = new SupplierDao();
        List<Suppliers> suppliers = supplierDao.getAll();
        Suppliers supplier = supplierDao.getById(idVaccine);

        try {
            logDao.insertLog("INFO", "Lấy danh sách nhà cung cấp, tổng số: " + suppliers.size(), "system", userIp);
            logDao.insertLog("INFO", "Nhà cung cấp của vắc xin id " + idVaccine + ": " + (supplier != null ? supplier.getName() : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AgeGroupDao ageDao = new AgeGroupDao();
        List<AgeGroups> ages = ageDao.getAgeGroups();
        AgeGroups age = ageDao.getAgeById(idVaccine);

        try {
            logDao.insertLog("INFO", "Lấy danh sách nhóm tuổi, tổng số: " + ages.size(), "system", userIp);
            logDao.insertLog("INFO", "Nhóm tuổi của vắc xin id " + idVaccine + ": " + (age != null ? age.getName() : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DisaseGroupDao disaseDao = new DisaseGroupDao();
        List<DisaseGroups> disases = disaseDao.getDisaseGroups();
        DisaseGroups disase = disaseDao.getDisaseById(idVaccine);

        try {
            logDao.insertLog("INFO", "Lấy danh sách nhóm bệnh, tổng số: " + disases.size(), "system", userIp);
            logDao.insertLog("INFO", "Nhóm bệnh của vắc xin id " + idVaccine + ": " + (disase != null ? disase.getName() : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VaccineDao vaccineDao = new VaccineDao();
        Vaccines vaccine = vaccineDao.getVaccineById(idVaccine);

        try {
            logDao.insertLog("INFO", "Thông tin vắc xin: " + (vaccine != null ? vaccine.getName() : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VacccineDetailDao detailDao = new VacccineDetailDao();
        VacccineDetails vdetail = detailDao.getVaccineDetailsById(idVaccine);

        try {
            logDao.insertLog("INFO", "Chi tiết vắc xin: " + (vdetail != null ? "đã lấy được" : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VaccineContentDao contentDao = new VaccineContentDao();
        VaccineContents vcontent = contentDao.getVaccineContentsById(idVaccine);

        try {
            logDao.insertLog("INFO", "Nội dung vắc xin: " + (vcontent != null ? "đã lấy được" : "null"), "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("v", vaccine);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("supplier", supplier);
        request.setAttribute("ages", ages);
        request.setAttribute("age", age);
        request.setAttribute("disases", disases);
        request.setAttribute("disase", disase);
        request.setAttribute("vdetail", vdetail);
        request.setAttribute("vcontent", vcontent);

        try {
            logDao.insertLog("INFO", "Chuyển tiếp đến trang cập nhật vắc xin", "system", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("update-vacxin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST ở đây
    }
}

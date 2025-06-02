package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.google.gson.Gson;
import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExportVaccine", value = "/admin/exportVaccine")
public class ExportVaccine extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        VaccineDao dao = new VaccineDao();

        // Ghi log trước khi xuất dữ liệu
        System.out.println("ExportVaccine: Starting to export vaccine data from IP: " + userIp);
        try {
            logDao.insertLog("INFO", "Start exporting vaccine data", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> vaccineData = dao.export();

        // Log số bản ghi xuất ra
        System.out.println("ExportVaccine: Number of vaccine records exported: " + vaccineData.size());
        try {
            logDao.insertLog("INFO", "Exported " + vaccineData.size() + " vaccine records", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển dữ liệu sang JSON
        String json = new Gson().toJson(vaccineData);

        // Ghi JSON vào response
        response.getWriter().write(json);

        // Log hoàn thành xuất dữ liệu
        System.out.println("ExportVaccine: Export vaccine data completed successfully from IP: " + userIp);
        try {
            logDao.insertLog("INFO", "Completed exporting vaccine data", "admin", userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong servlet này
    }
}

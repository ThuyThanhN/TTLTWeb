package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListVaccineAdmin", value = "/admin/table-data-vacxin")
public class ListVaccineAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách vắc xin
            VaccineDao vaccine = new VaccineDao();
            List<Vaccines> vaccines = vaccine.getAll();

            request.setAttribute("vaccines", vaccines);

            // Ghi log truy cập danh sách vắc xin admin
            logDao.insertLog("INFO", "Accessed vaccine list for admin", "anonymous", userIp);

            // Chuyển tiếp đến trang hiển thị dữ liệu vắc xin
            request.getRequestDispatcher("table-data-vacxin.jsp").forward(request, response);
        } catch (Exception e) {
            // Ghi log lỗi truy cập danh sách vắc xin admin
            try {
                logDao.insertLog("ERROR", "Error loading vaccine list for admin: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách vắc xin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

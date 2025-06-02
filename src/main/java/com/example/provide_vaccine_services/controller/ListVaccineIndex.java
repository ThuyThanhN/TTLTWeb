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

@WebServlet(name = "ListVaccineIndex", value = "/index")
public class ListVaccineIndex extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Lấy danh sách vắc xin
            VaccineDao vaccineDao = new VaccineDao();
            // Lấy danh sách 8 vắc xin có nhiều lượt đặt nhất
            List<Vaccines> topVaccines = vaccineDao.getTopVaccines();
            // Lấy danh sách 8 vắc xin ngẫu nhiên để hiển thị trong danh mục
            List<Vaccines> randomVaccines = vaccineDao.getRandomVaccines();

            // Đặt danh sách vào request
            request.setAttribute("topVaccines", topVaccines);
            request.setAttribute("randomVaccines", randomVaccines);

            // Ghi log truy cập trang index thành công
            logDao.insertLog("INFO", "Accessed index page with vaccine listings", "anonymous", userIp);

            // Chuyển tiếp đến trang index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            // Ghi log lỗi nếu có exception
            try {
                logDao.insertLog("ERROR", "Error loading vaccine lists on index page: " + e.getMessage(), "anonymous", userIp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải trang chính");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}

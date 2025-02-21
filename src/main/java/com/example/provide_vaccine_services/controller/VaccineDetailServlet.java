package com.example.provide_vaccine_services.controller;


import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.VaccineContents;
import com.example.provide_vaccine_services.dao.model.VaccineDetails;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "VaccineDetailServlet", value = "/detail_vaccines")
public class VaccineDetailServlet extends HttpServlet {
    private VaccineDao vaccineDao;

    @Override
    public void init() {
        vaccineDao = new VaccineDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vaccine ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Lấy thông tin vắc-xin từ DAO
            Map<String, Object> vaccineData = vaccineDao.getVaccineDetailsById(id);

            // Lấy đối tượng VaccineContents từ Map
            VaccineContents vaccineContents = (VaccineContents) vaccineData.get("vaccineContents");
            VaccineDetails vaccineDetails = (VaccineDetails) vaccineData.get("vaccineDetails");
            // Kiểm tra nếu không tìm thấy vaccineContents
            if (vaccineContents == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaccine not found");
                return;
            }

            // Truyền các đối tượng vào request
            request.setAttribute("vaccineContents", vaccineContents);
            request.setAttribute("vaccineDetails", vaccineDetails);

            // Chuyển tiếp đến file detail_vaccines.jsp
            request.getRequestDispatcher("detail_vaccines.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vaccine ID format");
        }
    }
}
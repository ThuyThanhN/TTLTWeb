package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListVaccineInfo", value = "/vaccine-information")
public class ListVaccineInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null) {
            handleAjaxSearch(request, response);
        } else {
            request.getRequestDispatcher("vaccine-information.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }

    // xử lí tìm kiếm sản phẩm trong trang
    private void handleAjaxSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = "";
        int totalVaccine = 0;
        String searchQuery = request.getParameter("query");
        String action = request.getParameter("action");
        boolean age = "boolean".equals(request.getParameter("age"));
        boolean disease = "disease".equals(request.getParameter("disease"));
        int pageNumber = parseIntOrDefault(request.getParameter("page"), 1);


        // xử lí format thời gian
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        List<Vaccines> vaccines;
        VaccineDao vaccineDao = new VaccineDao();

        switch (action) {
            case "search": {
                totalVaccine = vaccineDao.getTotalCount(searchQuery);
                vaccines = vaccineDao.getSearchedVaccinesByPage(searchQuery, pageNumber, age, disease);
                break;
            }
            default: {
                totalVaccine = vaccineDao.getTotalCount();
                vaccines = vaccineDao.getVaccinesByPage(pageNumber, age, disease);
                break;
            }

        }

        //tổng số trang
        int totalPages = (totalVaccine + 11) / 12; // Giả sử mỗi trang có 12 sản phẩm

        // map json
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("totalVaccine", totalVaccine);
        jsonMap.put("pageNumber", pageNumber);
        jsonMap.put("searchQuery", searchQuery);
        jsonMap.put("totalPages", totalPages);
        jsonMap.put("vaccines", vaccines);

        // chuyển thành json
        jsonString = mapper.writeValueAsString(jsonMap);

        // Gửi dữ liệu JSON về client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }

    private void SearchByAge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}


    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
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
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "search": {
                try {
                    logDao.insertLog("INFO", "Handle AJAX search request", "anonymous", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handleAjaxSearch(request, response);
                break;
            }
            case "autoComplete": {
                try {
                    logDao.insertLog("INFO", "Handle autocomplete request", "anonymous", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handleAutoComplete(request, response);
                break;
            }
            default: {
                try {
                    logDao.insertLog("INFO", "Forward to vaccine-information.jsp", "anonymous", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.getRequestDispatcher("vaccine-information.jsp").forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }

    private void handleAutoComplete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String jsonString = "";
        if (query != null && !query.trim().isEmpty()) {
            VaccineDao vaccineDao = new VaccineDao();
            List<String> suggestions = vaccineDao.getAutoCompleteSuggestions(query);

            // Debug log từng suggestion ra console
            for (String s : suggestions) {
                System.out.println(s);
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("suggestions", suggestions);

            jsonString = mapper.writeValueAsString(jsonMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonString);
        }
    }

    private void handleAjaxSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = "";
        int totalVaccine = 0;
        String searchQuery = request.getParameter("query");
        boolean age = Boolean.parseBoolean(request.getParameter("age"));
        boolean disease = Boolean.parseBoolean(request.getParameter("disease"));
        int pageNumber = parseIntOrDefault(request.getParameter("page"), 1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        VaccineDao vaccineDao = new VaccineDao();
        List<Vaccines> vaccines;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            totalVaccine = vaccineDao.getTotalCount(searchQuery, age, disease);
            vaccines = vaccineDao.getSearchedVaccinesByPage(searchQuery, pageNumber, age, disease);
        } else {
            totalVaccine = vaccineDao.getTotalCount(age, disease);
            vaccines = vaccineDao.getVaccinesByPage(pageNumber, age, disease);
        }

        int totalPages = (totalVaccine + 11) / 12; // Mỗi trang có 12 sản phẩm

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("totalVaccine", totalVaccine);
        jsonMap.put("pageNumber", pageNumber);
        jsonMap.put("searchQuery", searchQuery);
        jsonMap.put("totalPages", totalPages);
        jsonMap.put("vaccines", vaccines);

        jsonString = mapper.writeValueAsString(jsonMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}

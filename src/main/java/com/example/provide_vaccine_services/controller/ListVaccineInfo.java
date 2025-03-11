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
import java.util.List;

@WebServlet(name = "ListVaccineInfo", value = "/vaccine-information")
public class ListVaccineInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // kiểm tra xem gọi doGet bằng action nào
        if ("search".equals(action)) {
            handleAjaxSearch(request, response); // Gọi phương thức xử lý AJAX
        } else {
            handlePageRequest(request, response); // Gọi request bình thường ( reload trang )
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }

    // xử lí tìm kiếm sản phẩm trong trang
    private void handleAjaxSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("query");
        VaccineDao vaccineDao = new VaccineDao();
        System.out.println("query: " + searchQuery);
        // tìm danh sách theo từ khoá
        List<Vaccines> vaccines = vaccineDao.searchByName(searchQuery);

        System.out.println("vaccines: " + vaccines);
        // chuyển đổi thành json
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonString = mapper.writeValueAsString(vaccines);

        System.out.println(jsonString);

        // Gửi dữ liệu JSON về client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);


    }

    // xử lí khi tải trang
    private void handlePageRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin trang hiện tại từ query parameter, nếu không có thì mặc định là trang 1
        String pageSize = request.getParameter("page");
        if (pageSize == null || pageSize.isEmpty()) {
            pageSize = "1";  // Mặc định trang 1
        }
        int page = Integer.parseInt(pageSize);

        // Lấy các tham số tìm kiếm và lọc
        String searchQuery = request.getParameter("searchQuery");

        // Khởi tạo VaccineDao
        VaccineDao vaccineDao = new VaccineDao();

        // Lấy tổng số lượng vắc xin trong DB
        int count = vaccineDao.getTotalCount();
        int totalPages = (int) Math.ceil((double) count / 12); // Giả sử mỗi trang có 12 sản phẩm

        // Kiểm tra nếu trang cuối cùng không có sản phẩm
        if (count % 12 == 0 && count != 0) {
            totalPages--; // Giảm tổng số trang nếu trang cuối cùng không có sản phẩm
        }

        // Danh sách vắc xin sẽ được tìm kiếm và lọc
        List<Vaccines> vaccines;

        // Nếu có từ khóa tìm kiếm thì tìm vắc xin theo tên
        if (searchQuery != null && !searchQuery.isEmpty()) {
            vaccines = vaccineDao.searchByName(searchQuery);  // Tìm kiếm vắc xin theo tên
        }
        // Nếu không có lọc, phân trang vắc xin
        else {
            vaccines = vaccineDao.getVaccinesByPage(page); // Phân trang vắc xin
        }

        // Gửi dữ liệu đến JSP
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("vaccines", vaccines);
        request.setAttribute("searchQuery", searchQuery); // Truyền lại searchQuery cho form tìm kiếm

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("vaccine-information.jsp").forward(request, response);
    }
}

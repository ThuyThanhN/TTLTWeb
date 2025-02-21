package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListVaccineAge", value = "/age")
public class ListVaccineAge extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin trang hiện tại từ query parameter, nếu không có thì mặc định là trang 1
        String pageSize = request.getParameter("page");
        if (pageSize == null || pageSize.isEmpty()) {
            pageSize = "1"; // Mặc định trang 1
        }
        int page = Integer.parseInt(pageSize);

        // Khởi tạo VaccineDao
        VaccineDao vaccineDao = new VaccineDao();

        // Lấy tổng số lượng vắc xin theo nhóm tuổi
        int count = vaccineDao.getTotalCount();
        int totalPages = (int) Math.ceil((double) count / 12); // Mỗi trang có 12 sản phẩm

        // Nếu trang cuối không có sản phẩm
        if (count % 12 == 0 && count != 0) {
            totalPages--;
        }

        // Lấy danh sách vắc xin theo nhóm tuổi và phân trang
        List<Vaccines> vaccinesAge = vaccineDao.getVaccinesByPage(page);

        // Gửi dữ liệu đến JSP
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("vaccinesAge", vaccinesAge);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("age.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không cần xử lý POST trong trường hợp này
    }
}
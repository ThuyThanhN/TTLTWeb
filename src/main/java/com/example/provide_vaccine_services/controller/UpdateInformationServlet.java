package com.example.provide_vaccine_services.controller;


import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "UpdateInformationServlet", value = "/updateInformation")
public class UpdateInformationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý cập nhật thông tin (giữ nguyên code cũ)
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy các tham số từ form
        String fullname = request.getParameter("fullname");
        String identification = request.getParameter("identification");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        // Lấy các tham số địa chỉ
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");

        // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
        Date dateOfBirth = null;
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            dateOfBirth = Date.valueOf(dateOfBirthStr);
        }

        // Cập nhật thông tin cho đối tượng user
        user.setFullname(fullname);
        user.setIdentification(identification);
        user.setPhone(phone);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address );
        user.setProvince(province);
        user.setDistrict(district);
        user.setWard(ward);

        // Cập nhật dữ liệu vào cơ sở dữ liệu
        UserDao userDao = new UserDao();
        int result = userDao.updateUserDetails(user);


        if (result > 0) {
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            session.setAttribute("user", user);
            response.sendRedirect("information.jsp");
        } else {
            request.setAttribute("error", "Cập nhật thông tin thất bại!");
            request.getRequestDispatcher("information.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến trang thông tin cá nhân nếu truy cập bằng GET
        response.sendRedirect("information.jsp");
    }
}

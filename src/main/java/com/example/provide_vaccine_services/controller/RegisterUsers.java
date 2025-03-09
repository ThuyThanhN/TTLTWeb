package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
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

@WebServlet(name = "RegisterUsers", value = "/registerUsers")
public class RegisterUsers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị form đăng ký
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đảm bảo dữ liệu từ form được mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String identification = request.getParameter("identification");
        String dob = request.getParameter("dob");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");  // Thêm SĐT
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Mã hóa mật khẩu
        String hashedPassword = MD5Hash.hashPassword(password);

        // Tạo đối tượng user
        Users user = new Users(fullname, gender, identification, Date.valueOf(dob), address, province, district, ward, phone, email, hashedPassword, 0);

        // Lưu dữ liệu vào cơ sở dữ liệu
        UserDao userDao = new UserDao();
        int result = userDao.insertUser(user);

        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("user");

        if (result > 0) {
            if (users != null) {
                response.sendRedirect("table-data-user");
            } else {
                response.sendRedirect("login");
            }
        } else {
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

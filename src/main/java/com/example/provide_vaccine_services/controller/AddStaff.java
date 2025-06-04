package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "AddStaff", value = "/admin/addStaff")
public class AddStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý GET trong servlet này
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        String name = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String ident = request.getParameter("ident");
        String date = request.getParameter("date");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String module = request.getParameter("module");
        String pass = request.getParameter("password");

        int roleValue = role.equals("1") ? 1 : 2;
        Date sqlDate = Date.valueOf(date);

        // Mã hóa mật khẩu
        String hashedPassword = MD5Hash.hashPassword(pass);

        // Tạo user mới
        Users user = new Users(name, gender, ident, sqlDate, address,
                province, district, ward, phone, email, hashedPassword, roleValue);

        UserDao dao = new UserDao();
        int idUser = dao.insertStaff(user);

        int idPermission = dao.insertPermission(module);
        dao.insertUserPermission(idUser, idPermission);

        try {
            if (idUser > 0) {
                logDao.insertLog("INFO", "Thêm nhân viên thành công, idUser: " + idUser + ", module: " + module, email, userIp);
                response.getWriter().write("{\"status\":\"success\", \"id\":" + idUser + "}");
            } else {
                logDao.insertLog("ERROR", "Thêm nhân viên thất bại, idUser: " + idUser + ", module: " + module, email, userIp);
                response.getWriter().write("{\"status\":\"error\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi ghi log\"}");
        }
    }
}

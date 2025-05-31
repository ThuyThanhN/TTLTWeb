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

@WebServlet(name = "UpdateStaff", value = "/admin/updateStaff")
public class UpdateStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No implementation as per original code
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        String id = request.getParameter("id");
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
        String pass = request.getParameter("password");

        int idStaff = Integer.parseInt(id);
        int roleValue = role.equals("Admin") ? 1 : 2;
        Date sqlDate = Date.valueOf(date);
        String hashedPassword = MD5Hash.hashPassword(pass);
        UserDao userDao = new UserDao();
        Users user = new Users(idStaff, name, gender, ident, sqlDate, address,
                province, district, ward, phone, email, pass, roleValue);
        int result = userDao.update(user);

        if (result > 0) {
            try {
                logDao.insertLog("INFO", "Staff updated successfully: ID=" + idStaff + ", Name=" + name + ", Role=" + roleValue, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"status\":\"success\", \"fullname\":\"" + name + "\", \"gender\":\"" + gender + "\", \"ident\":\"" + ident + "\", \"date\":\"" + date + "\", \"email\":\"" + email + "\", \"address\":\"" + address + "\", \"province\":\"" + province + "\", \"district\":\"" + district + "\", \"ward\":\"" + ward + "\", \"phone\":\"" + phone + "\", \"role\":\"" + roleValue + "\", \"password\":\"" + hashedPassword + "\"}");
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update staff: ID=" + idStaff + ", Name=" + name, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Update failed.\"}");
        }
    }
}

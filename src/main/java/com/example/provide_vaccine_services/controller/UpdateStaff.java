package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateStaff", value = "/admin/updateStaff")
public class UpdateStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String module = request.getParameter("module");

        int idStaff = Integer.parseInt(id);
        int roleValue = role.equals("1") ? 1 : 2;
        Date sqlDate = Date.valueOf(date);
        String hashedPassword = MD5Hash.hashPassword(pass);

        UserDao userDao = new UserDao();
        Users user = new Users(idStaff, name, gender, ident, sqlDate,  address,
                province, district, ward, phone, email, hashedPassword,  roleValue);
        int updateUser = userDao.update(user); // cập nhật users

        // Lấy permissionId từ module
        int permissionId = userDao.getPermissionIdByModule(module); // cần thêm hàm này trong UserDao

        if (permissionId > 0) {
            // Cập nhật user_permission
            userDao.updateUserPermission(idStaff, permissionId); // cập nhật bảng userpermissions
        } else {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Update failed.\"}");
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = new HashMap<>();

        if (updateUser > 0) {
            jsonMap.put("status", "success");
            jsonMap.put("id", idStaff);
            jsonMap.put("fullname", name);
            jsonMap.put("gender", gender);
            jsonMap.put("ident", ident);
            jsonMap.put("date", date);
            jsonMap.put("email", email);
            jsonMap.put("address", address);
            jsonMap.put("province", province);
            jsonMap.put("district", district);
            jsonMap.put("ward", ward);
            jsonMap.put("phone", phone);
            jsonMap.put("role", roleValue);
            jsonMap.put("module", module);


            try {
                logDao.insertLog("INFO", "Staff updated successfully: ID=" + idStaff + ", Name=" + name + ", Role=" + roleValue, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write(mapper.writeValueAsString(jsonMap));
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonMap.put("status", "error");
            jsonMap.put("message", "Cập nhật thất bại.");

            try {
                logDao.insertLog("ERROR", "Failed to update staff: ID=" + idStaff + ", Name=" + name, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write(mapper.writeValueAsString(jsonMap));
        }
    }
}
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

@WebServlet(name = "FacebookLoginServlet", value = "/facebookLogin")
public class FacebookLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fbUserId = request.getParameter("fbUserId");
        String fbUserName = request.getParameter("fbUserName");
        String fbUserEmail = request.getParameter("fbEmail");

        UserDao userDao = new UserDao();
        Users user = null;

        // Tìm user theo fbUserId hoặc fbUserEmail
        if (fbUserId != null && !fbUserId.isEmpty()) {
            user = userDao.getUserByFaceBookId(fbUserId);
        }
        if (user == null) {
            // Nếu chưa có user, tạo mới
            user = new Users();
            user.setFullname(fbUserName);
            user.setEmail(fbUserEmail);
            user.setRole(0); // user thường
            user.setStatus(1); // mặc định là đã xác thực nếu qua FB
            Long facebookId = Long.parseLong(fbUserId);
            user.setFacebookId(facebookId);
            userDao.insertFBUser(user);
        }

        // Lưu user vào session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Chuyển hướng sau khi đăng nhập thành công
        if (user.getRole() == 1) {
            response.sendRedirect("admin/dashboard");
        } else {
            response.sendRedirect("index");
        }
    }
}

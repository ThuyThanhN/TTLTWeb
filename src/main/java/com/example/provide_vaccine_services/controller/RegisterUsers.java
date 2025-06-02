package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.Service.TokenGenerator;
import com.example.provide_vaccine_services.Service.EmailSender;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "RegisterUsers", value = "/registerUsers")
public class RegisterUsers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward đến trang đăng ký
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập mã hóa ký tự request UTF-8
        request.setCharacterEncoding("UTF-8");

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        // Lấy dữ liệu form đăng ký
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String identification = request.getParameter("identification");
        String dob = request.getParameter("dob");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra mật khẩu xác nhận có khớp không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = MD5Hash.hashPassword(password);

        // Tạo đối tượng user mới với trạng thái chưa xác thực (0)
        Users user = new Users(fullname, gender, identification, Date.valueOf(dob), address,
                province, district, ward, phone, email, hashedPassword, 0, 0);

        UserDao userDao = new UserDao();
        int result = userDao.insert(user);

        if (result > 0) {
            // Ghi log đăng ký thành công
            try {
                logDao.insertLog("INFO", "User registered successfully: " + email, email, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Tạo token xác thực
            String token = TokenGenerator.generateActivationToken();

            // Lưu token vào DB
            boolean isTokenSaved = userDao.saveVerificationToken(email, token);
            if (!isTokenSaved) {
                try {
                    logDao.insertLog("ERROR", "Failed to save verification token for: " + email, email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.setAttribute("error", "Lỗi khi lưu token xác thực!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Mã hóa token để dùng trong URL
            String encodedToken = encodeToken(token);

            // Tạo link xác thực tài khoản
            String verificationLink = "http://provide_vaccine_services_war/verifyAccount?token=" + encodedToken;

            System.out.println("Verification link: " + verificationLink); // Debug

            // Gửi email xác thực
            String subject = "Xác thực tài khoản";
            String body = "Chào bạn,\n\nVui lòng nhấn vào liên kết dưới đây để xác thực tài khoản của bạn:\n" + verificationLink;
            EmailSender.sendEmail(email, subject, body);

            // Ghi log gửi email thành công
            try {
                logDao.insertLog("INFO", "Verification email sent to: " + email, email, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Chuyển hướng đến trang login
            response.sendRedirect("login");
        } else {
            // Ghi log đăng ký thất bại
            try {
                logDao.insertLog("ERROR", "User registration failed: " + email, email, userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Hiển thị lỗi đăng ký
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Mã hóa token trước khi dùng trong URL
    private String encodeToken(String token) {
        try {
            return URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // Trả về null nếu lỗi
        }
    }
}

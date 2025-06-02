package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.Service.TokenGenerator;
import com.example.provide_vaccine_services.Service.EmailSender;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
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
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin từ form đăng ký
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

        // Kiểm tra mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = MD5Hash.hashPassword(password);

        // Tạo đối tượng User với trạng thái mặc định là 0 (chưa xác thực)
        Users user = new Users(fullname, gender, identification, Date.valueOf(dob), address,
                province, district, ward, phone, email, hashedPassword, 0, 0);

        UserDao userDao = new UserDao();
        int result = userDao.insert(user);

        if (result > 0) {
            // Tạo mã token xác thực
            String token = TokenGenerator.generateActivationToken();  // Sử dụng hàm generateActivationToken để tạo token

            // Lưu token vào cơ sở dữ liệu
            boolean isTokenSaved = userDao.saveVerificationToken(email, token);  // Lưu token vào cơ sở dữ liệu

            if (!isTokenSaved) {
                request.setAttribute("error", "Lỗi khi lưu token xác thực!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Mã hóa token trước khi sử dụng trong URL
            String encodedToken = encodeToken(token);  // Mã hóa token tại đây

            // Tạo URL xác thực
            String verificationLink = "http://vaccine.io.vn/verifyAccount?token=" + encodedToken;

            // In thông tin liên kết xác thực ra console để kiểm tra
            System.out.println("Verification link: " + verificationLink);  // Dòng lệnh debug

            // Gửi email xác thực
            String subject = "Xác thực tài khoản";
            String body = "Chào bạn,\n\nVui lòng nhấn vào liên kết dưới đây để xác thực tài khoản của bạn:\n" + verificationLink;
            EmailSender.sendEmail(email, subject, body);

            // Redirect người dùng đến trang login
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Phương thức mã hóa token trước khi sử dụng trong URL
    private String encodeToken(String token) {
        try {
            return URLEncoder.encode(token, "UTF-8");  // Mã hóa token tại đây
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;  // Nếu có lỗi, trả về null
        }
    }

}

package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.GoogleLogin;
import com.example.provide_vaccine_services.Service.TokenGenerator;
import com.example.provide_vaccine_services.Service.vnpay.VerifyRecaptcha;
import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        UserDao userDao = new UserDao();

        String userIp = request.getRemoteAddr();

        String code = request.getParameter("code");
        String provider = request.getParameter("provider");

        if (code == null || code.isEmpty()) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        Users authUser = authenticateUser(code, provider);
        if (authUser == null) {
            session.setAttribute("error", "login.jsp?error=invalid_auth");
            return;
        }

        Users user = userDao.getUserByEmail(authUser.getEmail());
        if (user == null) {
            authUser.setRole(0);
            String rawPassword = userDao.insertGGUser(authUser);
            user = authUser;

            String topic = "Mật khẩu đăng nhập TTT";
            String body = "Mật khẩu của bạn là: " + rawPassword;
            EmailSender.sendEmail(user.getEmail(), topic, body);
        }

        session.setAttribute("user", user);

        if (user.getRole() == 1) {
            response.sendRedirect("admin/dashboard");
        } else if (user.getRole() == 0) {
            response.sendRedirect("index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();  // Khởi tạo log DAO
        String userIp = request.getRemoteAddr();  // Lấy IP client
        request.setCharacterEncoding("UTF-8");

        // Lấy token reCAPTCHA từ form
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println("Token g-recaptcha-response nhận được: " + gRecaptchaResponse);

        // Kiểm tra token có null hoặc rỗng không
        if (gRecaptchaResponse == null || gRecaptchaResponse.trim().isEmpty()) {
            System.out.println("KHÔNG NHẬN ĐƯỢC TOKEN reCAPTCHA từ client");
            response.getWriter().write("captcha_missing"); // Mã lỗi riêng cho thiếu token
            return; // Dừng xử lý
        }

        // Nếu có token, tiến hành verify với Google
        boolean captchaVerified = VerifyRecaptcha.verify(gRecaptchaResponse);
        if (!captchaVerified) {
            System.out.println("TOKEN reCAPTCHA KHÔNG HỢP LỆ hoặc XÁC THỰC THẤT BẠI");
            response.getWriter().write("captcha_failed"); // Mã lỗi cho token sai hoặc verify thất bại
            return; // Dừng xử lý
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // Kiểm tra thời gian khóa tài khoản nếu có
        Long lockTime = (Long) session.getAttribute("lockTime");
        if (lockTime != null && System.currentTimeMillis() - lockTime < 60000) {
            response.getWriter().write("locked");
            return;
        }

        UserDao userDao = new UserDao();
        Users user = userDao.checkLogin(username, password);

        if (user == null) {
            try {
                logDao.insertLog("WARN", "Failed login attempt for username: " + username, username, userIp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Integer failedAttempts = (Integer) session.getAttribute("failedLoginAttempts");
            if (failedAttempts == null) failedAttempts = 0;
            failedAttempts++;
            session.setAttribute("failedLoginAttempts", failedAttempts);

            if (failedAttempts >= 5) {
                session.setAttribute("lockTime", System.currentTimeMillis());
                response.getWriter().write("locked");
            } else {
                response.getWriter().write("error");
            }
            return;
        }

        if (user.getStatus() == 0) {
            try {
                logDao.insertLog("WARN", "Login attempt with unverified account", user.getEmail(), userIp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (request.getHeader("X-Requested-With") != null) {
                response.getWriter().write("not_verified");
                sendActivationEmail(user.getEmail());
            }
            return;
        } else if (user.getStatus() == -1) {
            try {
                logDao.insertLog("WARN", "Login attempt with locked account", user.getEmail(), userIp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (request.getHeader("X-Requested-With") != null) {
                response.getWriter().write("lockAccount");
                sendActivationEmail(user.getEmail());
            }
            return;
        }

        // Đăng nhập thành công, reset số lần đăng nhập sai và thời gian khóa
        session.setAttribute("failedLoginAttempts", 0);
        session.removeAttribute("lockTime");

        try {
            logDao.insertLog("INFO", "User logged in successfully", user.getEmail(), userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);

        if (request.getHeader("X-Requested-With") != null) {
            if (user.getRole() == 1) {
                response.getWriter().write("admin/dashboard");
            } else if (user.getRole() == 0) {
                response.getWriter().write("index");
            }
        } else {
            if (user.getRole() == 1) {
                response.sendRedirect("admin/dashboard");
            } else if (user.getRole() == 0) {
                response.sendRedirect("index");
            }
        }
    }


    private void sendActivationEmail(String email) {
        String token = TokenGenerator.generateActivationToken();
        UserDao userDao = new UserDao();
        boolean isTokenSaved = userDao.saveVerificationToken(email, token);

        if (!isTokenSaved) {
            System.out.println("Lỗi khi lưu token xác thực vào cơ sở dữ liệu!");
            return;
        }

        String encodedToken = encodeToken(token);
        String verificationLink = "https://vaccine.io.vn/verifyAccount?token=" + encodedToken;

        String subject = "Xác thực tài khoản";
        String body = "Chào bạn,\n\nVui lòng nhấn vào liên kết dưới đây để xác thực tài khoản của bạn:\n" + verificationLink;

        EmailSender.sendEmail(email, subject, body);
        System.out.println("Email xác thực đã được gửi đến: " + email);
    }

    private String encodeToken(String token) {
        try {
            return URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Users authenticateUser(String code, String provider) throws IOException {
        GoogleLogin gg = new GoogleLogin();
        String accessToken = null;
        Users authUser = null;

        switch (provider) {
            case "google":
                accessToken = gg.getGGToken(code);
                if (accessToken != null && !accessToken.isEmpty()) {
                    authUser = gg.getGGUserInfo(accessToken);
                }
                break;
            case "facebook":
                accessToken = gg.getFBToken(code);
                if (accessToken != null && !accessToken.isEmpty()) {
                    authUser = gg.getFBUserInfo(accessToken);
                }
                break;
            default:
                System.out.println(provider + " invalid!!");
                break;
        }
        return authUser;
    }
}

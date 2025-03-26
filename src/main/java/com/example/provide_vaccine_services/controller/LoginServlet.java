package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.GoogleLogin;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Khi truy cập GET, chuyển hướng người dùng đến trang login.jsp
        response.setContentType("text/html;charset=UTF-8");
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

        UserDao userDao = new UserDao();
        Users user = userDao.getUserByEmail(authUser.getEmail());
        if (user == null) {
            authUser.setRole(0);
            userDao.insertGGUser(authUser);
            user = authUser;
        }

        session.setAttribute("user", user);

        // Kiểm tra vai trò và chuyển hướng trang
        if (user.getRole() == 1) {
            // Vai trò admin
            response.sendRedirect("admin/dashboard");
        } else if (user.getRole() == 0) {
            // Vai trò người dùng thường
            response.sendRedirect("index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đảm bảo dữ liệu từ form được mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");


        // Lấy thông tin đăng nhập từ form
        String email = request.getParameter("username");
        String password = request.getParameter("password");

        // nếu khng có ật khẩu hoặc dùng mật khẩu set cho các tài khoản google_auth
        if (password == null || password.isEmpty() || password.equals("GOOGLE_AUTH")) {
            request.setAttribute("error", "Invalid password");
        }

        // Gọi UserDao để kiểm tra thông tin đăng nhập
        UserDao userDao = new UserDao();
        Users user = userDao.checkLogin(email, password);

        if (user != null) {
            // Đăng nhập thành công, tạo session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Giới hạn thời gian session (ví dụ: 30 phút)
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            // Kiểm tra vai trò và chuyển hướng trang
            if (user.getRole() == 1) {
                // Vai trò admin
                response.sendRedirect("admin/dashboard");
            } else if (user.getRole() == 0) {
                // Vai trò người dùng thường
                response.sendRedirect("index");
            }
        } else {
            // Đăng nhập thất bại, quay lại login.jsp với thông báo lỗi
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private Users authenticateUser(String code, String provider) throws IOException {

        GoogleLogin gg = new GoogleLogin();

        String accessToken = null;
        Users authUser = null;

        if ("google".equalsIgnoreCase(provider)) {
            accessToken = gg.getGGToken(code);
            if (accessToken != null && !accessToken.isEmpty()) {
                authUser = gg.getGGUserInfo(accessToken);
            }
        } else if ("facebook".equalsIgnoreCase(provider)) {
            accessToken = gg.getFBToken(code);
            if (accessToken != null && !accessToken.isEmpty()) {
                authUser = gg.getFBUserInfo(accessToken);
            }
        }

        return authUser;
    }
}
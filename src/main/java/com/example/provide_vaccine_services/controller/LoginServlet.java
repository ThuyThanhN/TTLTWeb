package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Khi truy cập GET, chuyển hướng người dùng đến trang login.jsp
        LOGGER.info("moved to login page");
        // Lấy IP của client
        String clientIP = request.getRemoteAddr();

        // Đưa IP vào MDC để Log4j có thể sử dụng
        ThreadContext.put("ip", clientIP);

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đảm bảo dữ liệu từ form được mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin đăng nhập từ form
        String email = request.getParameter("username");
        String password = request.getParameter("password");

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
                response.sendRedirect("dashboard");
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
}
package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.GoogleLogin;
import com.example.provide_vaccine_services.Service.TokenGenerator;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    // Khởi tạo logger cho lớp TestService
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        UserDao userDao = new UserDao();


        // Lấy thông tin code và provider từ request
        String code = request.getParameter("code");
        String provider = request.getParameter("provider");

        // Lấy thông tin người dùng và IP từ request/session
        String userName = (String) session.getAttribute("user"); // Giả sử bạn lưu thông tin người dùng vào session
        String ip = request.getRemoteAddr(); // Lấy địa chỉ IP của người dùng

        // Ghi log thông báo khi người dùng truy cập trang login
        LOGGER.info("User: {} | IP: {} - moved to login page", userName, ip);


        // Nếu không có code => trả về trang login và kết thúc.
        if (code == null || code.isEmpty()) {
            LOGGER.info("No OAuth code received, redirecting to login page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Lấy dữ liệu người dùng bằng code và provider tương ứng
        Users authUser = authenticateUser(code, provider);
        if (authUser == null) {
            // Nếu không xác thực được người dùng, ghi log lỗi và chuyển hướng
            LOGGER.error("Failed to authenticate user with code: {} and provider: {}", code, provider);
            session.setAttribute("error", "login.jsp?error=invalid_auth");
            return;
        }

        // Kiểm tra xem user có tồn tại trong DB chưa
        Users user = userDao.getUserByEmail(authUser.getEmail());
        if (user == null) {
            // Nếu không có, tạo mới người dùng và lưu vào database
            authUser.setRole(0); // Thiết lập vai trò mặc định là người dùng thường
            userDao.insertGGUser(authUser);
            user = authUser;
        } else {
        }

        // Lưu người dùng vào session
        session.setAttribute("user", authUser.getEmail()); // Lưu lại email hoặc user vào session

        // Kiểm tra vai trò và chuyển hướng trang
        if (user.getRole() == 1) {
            // Vai trò admin
            response.sendRedirect("admin/dashboard");
        } else if (user.getRole() == 0) {
            // Vai trò người dùng thường
            // Ghi log khi người dùng được chuyển hướng tới trang index
            LOGGER.info("User: {} | IP: {} - Redirecting to the user dashboard (index page).", userName, ip);
            response.sendRedirect("index");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đảm bảo dữ liệu từ form được mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin đăng nhập từ form
        String username = request.getParameter("username"); // email hoặc số điện thoại
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");

        // Kiểm tra số lần đăng nhập sai
        if (loginAttempts == null) {
            loginAttempts = 0; // Nếu lần đầu tiên, khởi tạo số lần đăng nhập sai
        }

        // Kiểm tra thông tin đăng nhập
        UserDao userDao = new UserDao();
        Users user = userDao.checkLogin(username, password);

        if (user == null) {
            loginAttempts++;  // Tăng số lần đăng nhập sai
            session.setAttribute("loginAttempts", loginAttempts);  // Lưu số lần đăng nhập sai vào session

            if (loginAttempts >= 5) {
                // Nếu đã vượt quá 5 lần sai, khóa tài khoản trong một khoảng thời gian (ví dụ: 5 phút)
                session.setAttribute("lockTime", System.currentTimeMillis() + 5 * 60 * 1000); // Thời gian khóa 5 phút

                // Gửi phản hồi "locked" về client để thông báo tài khoản bị khóa
                response.getWriter().write("locked");
                return;
            } else {
                // Nếu đăng nhập sai nhưng chưa vượt quá 5 lần, trả về thông báo lỗi
                response.getWriter().write("error");
                return;
            }
        }

        // Nếu đăng nhập đúng
        session.setAttribute("user", user); // Lưu thông tin người dùng vào session
        session.setMaxInactiveInterval(30 * 60); // 30 phút

        // Kiểm tra trạng thái xác thực của tài khoản
        if (user.getStatus() == 0) {
            // Nếu chưa xác thực, gửi email xác thực
            if (request.getHeader("X-Requested-With") != null) {
                response.getWriter().write("not_verified");  // Gửi phản hồi cho AJAX để xử lý modal
                sendActivationEmail(user.getEmail()); // Gửi email xác thực
            } else {
                request.setAttribute("error", "Tài khoản chưa xác thực. Vui lòng kiểm tra email để xác thực tài khoản.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            return;
        }

        // Nếu đã xác thực, chuyển hướng đến trang chủ hoặc dashboard
        if (user.getRole() == 1) {
            response.sendRedirect("admin/dashboard");
        } else {
            response.sendRedirect("index");
        }
    }

    // Phương thức gửi email xác thực
    private void sendActivationEmail(String email) {
        // Tạo mã token xác thực
        String token = TokenGenerator.generateActivationToken();  // Tạo token để xác thực tài khoản

        // Lưu token vào cơ sở dữ liệu để liên kết với tài khoản người dùng
        UserDao userDao = new UserDao();
        boolean isTokenSaved = userDao.saveVerificationToken(email, token);  // Lưu token vào DB

        if (!isTokenSaved) {
            System.out.println("Lỗi khi lưu token xác thực vào cơ sở dữ liệu!");
            return; // Nếu không lưu được token, thoát khỏi phương thức
        }

        // Mã hóa token để có thể sử dụng trong URL
        String encodedToken = encodeToken(token);  // Mã hóa token

        // Tạo URL xác thực (địa chỉ này có thể thay đổi tùy thuộc vào cấu trúc của bạn)
        String verificationLink = "http://localhost:8080/provide_vaccine_services_war/verifyAccount?token=" + encodedToken;

        // Nội dung email
        String subject = "Xác thực tài khoản";
        String body = "Chào bạn,\n\nVui lòng nhấn vào liên kết dưới đây để xác thực tài khoản của bạn:\n" + verificationLink;

        // Gửi email xác thực
        EmailSender.sendEmail(email, subject, body);  // Sử dụng lớp EmailSender để gửi email
        System.out.println("Email xác thực đã được gửi đến: " + email); // Đoạn này để kiểm tra log
    }

    // Phương thức mã hóa token trước khi sử dụng trong URL
    private String encodeToken(String token) {
        try {
            return URLEncoder.encode(token, "UTF-8");  // Mã hóa token tại đây
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;  // Nếu có lỗi mã hóa, trả về null
        }
    }


    //  lấy người dùng từ code và provider
    private Users authenticateUser(String code, String provider) throws IOException {

        GoogleLogin gg = new GoogleLogin();
        String accessToken = null;
        Users authUser = null;

        /**
         *
         *  1) sử dụng switch case để kiểm tra xem provider là gì để gọi phương thức phù hợp ( ví dụ: google, facebook )
         *
         *  2) lấy access_token từ code
         *      accessToken = gg.getGGToken(code);
         *
         *  3) Lấy dữ liệu người dùng
         *
         *   + nếu lấy được thành công access_token
         *            if (accessToken != null && !accessToken.isEmpty()) {
         *              authUser = gg.getGGUserInfo(accessToken);
         *            }
         *
         *   + nếu không thì authUser là null;
         *
         * ví dụ:
         *  switch (provider) {
         *      case "google":
         *          accessToken = gg.getGGToken(code);
         *          if (accessToken != null && !accessToken.isEmpty()) {
         *              authUser = gg.getGGUserInfo(accessToken);
         *          }
         *          break;
         *  }
         */


        switch (provider) {
            case "google":
                accessToken = gg.getGGToken(code);
                if (accessToken != null && !accessToken.isEmpty()) {
                    authUser = gg.getGGUserInfo(accessToken);
                }
                break;
//            case "facebook":
//                accessToken = gg.getFBToken(code);
//                if (accessToken != null && !accessToken.isEmpty()) {
//                    authUser = gg.getFBUserInfo(accessToken);
//                }
//                break;

            // provider không hợp lệ
            default:
                System.out.println(provider + " invalid!!");
                break;
        }

        return authUser;
    }
}
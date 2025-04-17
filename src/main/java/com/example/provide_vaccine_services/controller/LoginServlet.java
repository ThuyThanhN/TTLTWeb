package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
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
        response.setContentType("text/html;charset=UTF-8");
        UserDao userDao = new UserDao();


        /**
         *
         * cách hoạt động của Oauth
         *
         * sau khi đăng nhập bên thứ 3 sẽ trả về code & provider đăng nhập ghi người dùng đăng nhập Oauth ( sẽ là null nếu không đăng nhạp oauth )
         * ví dụ: code = FNAFJKS... provider = "google", "facebook"
         *
         * sử dụng code đó để lấy được
         *  + ACCESS_TOKEN: truy cập vào dữ liệu người dùng của bên thứ 3
         *
         * bên thứ 3 sẽ kiểm tra ACCESS_TOKEN có hợp lệ hay không. nếu có thì trả về dữ liệu người dùng
         *
         */
        String code = request.getParameter("code");
        String provider = request.getParameter("provider");


        // nếu không có code => trả về trang login và kết thúc.
        if (code == null || code.isEmpty()) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // lấy dữ liệu người dùng bằng code và provider tương ứng
        Users authUser = authenticateUser(code, provider);
        if (authUser == null) {
            session.setAttribute("error", "login.jsp?error=invalid_auth");
            return;
        }

        /**
         *  kiểm tra xem user có tồn tại trong DB chưa
         *
         *  + nếu có => lấy thông tin người dùng đó và đăng nhập
         *  + nếu chưa => tạo người dùng mới lưu vào database
         *
         */
        Users user = userDao.getUserByEmail(authUser.getEmail());
        if (user == null) {
            authUser.setRole(0);
            String rawPassword = userDao.insertGGUser(authUser);
            user = authUser;
            // gửi mật khẩu về mail
            String topic = "Mật khẩu đăng nhập TTT";
            String body = "mật khẩu của bạn là: " + rawPassword;
            EmailSender.sendEmail(user.getEmail(), topic, body);
        }


        // lưu ngươi dùng vào session
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
        String username = request.getParameter("username"); // email hoặc số điện thoại
        String password = request.getParameter("password");

        // Gọi UserDao để kiểm tra thông tin đăng nhập
        UserDao userDao = new UserDao();
        Users user = userDao.checkLogin(username, password);

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
            case "facebook":
                accessToken = gg.getFBToken(code);
                if (accessToken != null && !accessToken.isEmpty()) {
                    authUser = gg.getFBUserInfo(accessToken);
                }
                break;

            // provider không hợp lệ
            default:
                System.out.println(provider + " invalid!!");
                break;
        }

        return authUser;
    }
}
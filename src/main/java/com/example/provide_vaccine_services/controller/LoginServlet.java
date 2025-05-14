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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        // Lấy session hiện tại
        HttpSession session = request.getSession();

        // Kiểm tra nếu tài khoản bị khóa (kiểm tra thời gian khóa trong session)
        Long lockTime = (Long) session.getAttribute("lockTime");
        if (lockTime != null && System.currentTimeMillis() - lockTime < 60000) {
            // Nếu thời gian khóa chưa hết 1 phút, trả về "locked"
            response.getWriter().write("locked");
            return;
        }

        // Gọi UserDao để kiểm tra thông tin đăng nhập
        UserDao userDao = new UserDao();
        Users user = userDao.checkLogin(username, password);

        // Kiểm tra nếu người dùng không tồn tại
        if (user == null) {
            // Nếu không tìm thấy người dùng, đăng nhập thất bại
            Integer failedAttempts = (Integer) session.getAttribute("failedLoginAttempts");
            if (failedAttempts == null) {
                failedAttempts = 0;  // Nếu chưa có, khởi tạo là 0
            }

            // Tăng số lần đăng nhập sai
            failedAttempts++;
            session.setAttribute("failedLoginAttempts", failedAttempts);

            // Kiểm tra nếu số lần đăng nhập sai vượt quá 5 lần
            if (failedAttempts >= 5) {
                // Lưu thời gian khóa vào session
                session.setAttribute("lockTime", System.currentTimeMillis());
                response.getWriter().write("locked");  // Trả về "locked" để yêu cầu người dùng thử lại sau 1 phút
            } else {
                response.getWriter().write("error");  // Trả về lỗi khi gọi AJAX
            }
            return;  // Dừng lại để không tiếp tục kiểm tra trạng thái và vai trò
        }

        // Kiểm tra trạng thái xác thực của tài khoản
        if (user.getStatus() == 0) {
            if (request.getHeader("X-Requested-With") != null) {
                response.getWriter().write("not_verified");  // Gửi phản hồi cho AJAX để xử lý modal
                sendActivationEmail(user.getEmail()); // Gửi email xác thực
            }
            return;
        } else if (user.getStatus() == -1) {
            if (request.getHeader("X-Requested-With") != null) {
                response.getWriter().write("lockAccount");  // Trả về phản hồi cho AJAX để hiển thị modal
                sendActivationEmail(user.getEmail());  // Gửi email xác thực
            }
            return;
        }

        // Nếu đăng nhập thành công, reset số lần đăng nhập sai và thời gian khóa
        session.setAttribute("failedLoginAttempts", 0);
        session.removeAttribute("lockTime");

        // Lưu thông tin người dùng vào session
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60); // 30 phút

        // Kiểm tra vai trò và chuyển hướng trang
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
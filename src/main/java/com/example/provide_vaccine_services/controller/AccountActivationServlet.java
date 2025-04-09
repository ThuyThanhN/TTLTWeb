package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.AccountActivationTokenGenerator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet(name = "AccountActivationServlet", value = "/send-activation-link")
public class AccountActivationServlet extends HttpServlet {

    // Thời gian hết hạn của mã token kích hoạt (ví dụ: 5 phút)
    private static final long TOKEN_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút (60 giây)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến trang gửi liên kết kích hoạt tài khoản
        request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy email người dùng từ request (được gửi từ form)
        String email = request.getParameter("email");

        // Kiểm tra nếu email hợp lệ
        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập email.");
            request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);
            return;
        }

        // Tạo mã token kích hoạt tài khoản bằng lớp AccountActivationTokenGenerator
        String token = AccountActivationTokenGenerator.generateActivationToken();

        // Tạo URL kích hoạt tài khoản chứa mã token
        String activationLink = generateActivationLink(token);

        // Gửi email với liên kết kích hoạt tài khoản
        boolean emailSent = sendActivationLinkEmail(email, activationLink);

        if (emailSent) {
            // Lưu mã token vào session hoặc cơ sở dữ liệu nếu cần
            request.getSession().setAttribute("activationToken", token);
            request.setAttribute("message", "Một liên kết kích hoạt đã được gửi đến email của bạn.");
        } else {
            request.setAttribute("error", "Có lỗi khi gửi email. Vui lòng thử lại sau.");
        }

        // Chuyển hướng đến trang kết quả
        request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);
    }

    /**
     * Tạo URL chứa mã token để kích hoạt tài khoản
     * @param token Mã token ngẫu nhiên tạo từ phương thức generateActivationToken
     * @return URL kích hoạt tài khoản chứa mã token
     */
    private String generateActivationLink(String token) {
        String baseUrl = "https://example.com/activate-account"; // Cập nhật URL của bạn ở đây
        return baseUrl + "?token=" + token; // Liên kết kích hoạt tài khoản
    }

    /**
     * Gửi email với liên kết kích hoạt tài khoản
     * @param email Địa chỉ email của người nhận
     * @param activationLink Liên kết kích hoạt tài khoản chứa mã token
     * @return true nếu email gửi thành công, false nếu có lỗi
     */
    private boolean sendActivationLinkEmail(String email, String activationLink) {
        try {
            // Tạo chủ đề và nội dung email
            String subject = "Kích hoạt tài khoản của bạn";
            String body = "<h3>Chào bạn,</h3>" +
                    "<p>Vui lòng nhấp vào liên kết dưới đây để kích hoạt tài khoản của bạn:</p>" +
                    "<a href='" + activationLink + "'>Kích hoạt tài khoản</a>";
            // Gửi email với liên kết kích hoạt
            EmailSender.sendEmail(email, subject, body); // Giả sử EmailSender là lớp gửi email của bạn
            return true; // Gửi email thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, trả về false
        }
    }
}

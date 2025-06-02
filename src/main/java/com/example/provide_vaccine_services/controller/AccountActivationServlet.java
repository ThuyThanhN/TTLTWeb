package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.TokenGenerator;
import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "AccountActivationServlet", value = "/send-activation-link")
public class AccountActivationServlet extends HttpServlet {

    private static final long TOKEN_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        try {
            logDao.insertLog("INFO", "GET request received at /send-activation-link", null, userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        try {
            logDao.insertLog("INFO", "POST request to send activation link for email: " + email, null, userIp);

            if (email == null || email.isEmpty()) {
                logDao.insertLog("WARN", "Empty email received in activation link request", null, userIp);
                request.setAttribute("error", "Vui lòng nhập email.");
                request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);
                return;
            }

            String token = TokenGenerator.generateActivationToken();
            String activationLink = generateActivationLink(token);

            boolean emailSent = sendActivationLinkEmail(email, activationLink);

            if (emailSent) {
                logDao.insertLog("INFO", "Activation link sent successfully to email: " + email, null, userIp);
                request.getSession().setAttribute("activationToken", token);
                request.setAttribute("message", "Một liên kết kích hoạt đã được gửi đến email của bạn.");
            } else {
                logDao.insertLog("ERROR", "Failed to send activation link email to: " + email, null, userIp);
                request.setAttribute("error", "Có lỗi khi gửi email. Vui lòng thử lại sau.");
            }

            request.getRequestDispatcher("send-activation-link.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                logDao.insertLog("ERROR", "Exception in sending activation link for email: " + email + " - " + e.getMessage(), null, userIp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống. Vui lòng thử lại sau.");
        }
    }

    private String generateActivationLink(String token) {
        String baseUrl = "https://example.com/activate-account";
        return baseUrl + "?token=" + token;
    }

    private boolean sendActivationLinkEmail(String email, String activationLink) {
        try {
            String subject = "Kích hoạt tài khoản của bạn";
            String body = "<h3>Chào bạn,</h3>" +
                    "<p>Vui lòng nhấp vào liên kết dưới đây để kích hoạt tài khoản của bạn:</p>" +
                    "<a href='" + activationLink + "'>Kích hoạt tài khoản</a>";
            EmailSender.sendEmail(email, subject, body);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

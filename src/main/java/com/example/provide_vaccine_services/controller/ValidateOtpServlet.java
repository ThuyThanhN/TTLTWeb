package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.Service.EmailSender;
import com.example.provide_vaccine_services.Service.OTPGenerator;
import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "ValidateOtpServlet", value = "/verify-reset-passwd")
public class ValidateOtpServlet extends HttpServlet {

    // Thời gian khóa sau khi nhập sai 3 lần OTP (1 phút)
    private static final long LOCK_DURATION = 60 * 1000; // 1 phút (60 giây)
    private static final long RESEND_INTERVAL = 60 * 1000; // Thời gian giữa các lần gửi mã OTP (1 phút)
    private static final int MAX_OTP_SENT_PER_DAY = 3; // Giới hạn số lần gửi mã OTP trong một ngày (3 lần)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến verify-reset-passwd.jsp nếu người dùng truy cập bằng GET
        request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        String email = (String) request.getSession().getAttribute("email");

        // Kiểm tra nếu có yêu cầu gửi lại OTP
        String resendOtp = request.getParameter("resendOtp");
        if (resendOtp != null && resendOtp.equals("true")) {
            resendOtp(request, response);
            return;
        }

        String enteredOtp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");
        Integer failedAttempts = (Integer) request.getSession().getAttribute("failedAttempts");
        Long lockTime = (Long) request.getSession().getAttribute("lockTime");

        try {
            if (lockTime != null && new Date().getTime() - lockTime < LOCK_DURATION) {
                // Log người dùng bị khóa do nhập sai OTP
                logDao.insertLog("WARN", "User temporarily locked due to multiple failed OTP attempts", email, userIp);

                long timeLeft = (LOCK_DURATION - (new Date().getTime() - lockTime)) / 1000;
                request.setAttribute("error", "Bạn đã nhập sai 3 lần. Vui lòng đợi 1 Phút để thử lại.");
                request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
                return;
            }

            if (sessionOtp != null && sessionOtp.equals(enteredOtp)) {
                // Log xác thực OTP thành công
                logDao.insertLog("INFO", "User verified OTP successfully", email, userIp);

                request.getSession().setAttribute("failedAttempts", 0);
                request.getSession().removeAttribute("lockTime");
                response.sendRedirect(request.getContextPath() + "/updatePasswd");
            } else {
                if (failedAttempts == null) {
                    failedAttempts = 0;
                }
                failedAttempts++;
                request.getSession().setAttribute("failedAttempts", failedAttempts);

                // Log nhập sai OTP
                logDao.insertLog("WARN", "User entered incorrect OTP attempt #" + failedAttempts, email, userIp);

                if (failedAttempts >= 3) {
                    request.getSession().setAttribute("lockTime", new Date().getTime());

                    // Log khóa tài khoản do nhập sai OTP 3 lần
                    logDao.insertLog("WARN", "User account locked due to 3 failed OTP attempts", email, userIp);
                }

                request.setAttribute("error", "Mã OTP không đúng! Bạn còn " + (3 - failedAttempts) + " lần thử.");
                request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }
    }

    private void resendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        String email = (String) request.getSession().getAttribute("email");

        Integer otpSentCount = (Integer) request.getSession().getAttribute("otpSentCount");
        Long lastOtpTime = (Long) request.getSession().getAttribute("lastOtpTime");

        try {
            if (otpSentCount != null && otpSentCount >= MAX_OTP_SENT_PER_DAY) {
                // Log gửi OTP vượt giới hạn
                logDao.insertLog("WARN", "User exceeded max OTP resend attempts for the day", email, userIp);

                response.getWriter().write("Bạn đã gửi quá 3 lần mã OTP trong ngày. Vui lòng thử lại vào ngày mai.");
                return;
            }

            if (lastOtpTime != null && new Date().getTime() - lastOtpTime < RESEND_INTERVAL) {
                // Log gửi OTP quá nhanh
                logDao.insertLog("WARN", "User attempted to resend OTP too soon", email, userIp);

                response.getWriter().write("Vui lòng đợi ít nhất 1 phút để gửi mã OTP lại.");
                return;
            }

            if (email != null) {
                String newOtp = OTPGenerator.generateOTP();
                EmailSender.sendEmail(email, "Reset Password OTP", "Mã OTP mới của bạn là: " + newOtp);

                request.getSession().setAttribute("otp", newOtp);
                request.getSession().setAttribute("otpCreatedTime", System.currentTimeMillis());
                request.getSession().setAttribute("lastOtpTime", new Date().getTime());

                otpSentCount = otpSentCount == null ? 1 : otpSentCount + 1;
                request.getSession().setAttribute("otpSentCount", otpSentCount);

                // Log gửi OTP thành công
                logDao.insertLog("INFO", "User requested OTP resend", email, userIp);

                response.getWriter().write("Mã OTP mới đã được gửi đến email của bạn.");
            } else {
                response.getWriter().write("Không tìm thấy email trong session.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi khi xử lý yêu cầu gửi lại OTP.");
        }
    }}

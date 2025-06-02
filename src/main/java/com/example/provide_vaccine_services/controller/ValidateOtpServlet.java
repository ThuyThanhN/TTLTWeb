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

    private static final long LOCK_DURATION = 60 * 1000; // 1 phút (60 giây)
    private static final long RESEND_INTERVAL = 60 * 1000; // 1 phút
    private static final int MAX_OTP_SENT_PER_DAY = 3; // 3 lần/ngày

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();
        String email = (String) request.getSession().getAttribute("email");

        String resendOtp = request.getParameter("resendOtp");
        if ("true".equals(resendOtp)) {
            resendOtp(request, response);
            return;
        }

        String enteredOtp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");
        Integer failedAttempts = (Integer) request.getSession().getAttribute("failedAttempts");
        Long lockTime = (Long) request.getSession().getAttribute("lockTime");

        try {
            if (lockTime != null && new Date().getTime() - lockTime < LOCK_DURATION) {
                try {
                    logDao.insertLog("WARN", "User temporarily locked due to multiple failed OTP attempts", email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.setAttribute("error", "Bạn đã nhập sai 3 lần. Vui lòng đợi 1 Phút để thử lại.");
                request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
                return;
            }

            if (sessionOtp != null && sessionOtp.equals(enteredOtp)) {
                try {
                    logDao.insertLog("INFO", "User verified OTP successfully", email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.getSession().setAttribute("failedAttempts", 0);
                request.getSession().removeAttribute("lockTime");
                response.sendRedirect(request.getContextPath() + "/updatePasswd");
            } else {
                if (failedAttempts == null) {
                    failedAttempts = 0;
                }
                failedAttempts++;
                request.getSession().setAttribute("failedAttempts", failedAttempts);

                try {
                    logDao.insertLog("WARN", "User entered incorrect OTP attempt #" + failedAttempts, email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (failedAttempts >= 3) {
                    request.getSession().setAttribute("lockTime", new Date().getTime());
                    try {
                        logDao.insertLog("WARN", "User account locked due to 3 failed OTP attempts", email, userIp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                request.setAttribute("error", "Mã OTP không đúng! Bạn còn " + (3 - failedAttempts) + " lần thử.");
                request.getRequestDispatcher("verify-reset-passwd.jsp").forward(request, response);
            }
        } catch (Exception e) {
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
                try {
                    logDao.insertLog("WARN", "User exceeded max OTP resend attempts for the day", email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.getWriter().write("Bạn đã gửi quá 3 lần mã OTP trong ngày. Vui lòng thử lại vào ngày mai.");
                return;
            }

            if (lastOtpTime != null && new Date().getTime() - lastOtpTime < RESEND_INTERVAL) {
                try {
                    logDao.insertLog("WARN", "User attempted to resend OTP too soon", email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

                try {
                    logDao.insertLog("INFO", "User requested OTP resend", email, userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.getWriter().write("Mã OTP mới đã được gửi đến email của bạn.");
            } else {
                response.getWriter().write("Không tìm thấy email trong session.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi khi xử lý yêu cầu gửi lại OTP.");
        }
    }
}

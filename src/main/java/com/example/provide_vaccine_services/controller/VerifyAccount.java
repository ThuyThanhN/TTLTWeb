package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

@WebServlet(name = "VerifyAccount", value = "/verifyAccount")
public class VerifyAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        String tokenFromUrl = request.getParameter("token");

        try {
            logDao.insertLog("INFO", "Received token from URL: " + tokenFromUrl, "anonymous", userIp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tokenFromUrl != null) {
            try {
                String decodedToken = URLDecoder.decode(tokenFromUrl, "UTF-8");
                try {
                    logDao.insertLog("INFO", "Decoded token: " + decodedToken, "anonymous", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                UserDao userDao = new UserDao();
                boolean isTokenValid = userDao.isTokenValid(decodedToken);

                try {
                    logDao.insertLog("INFO", "Token validity check result: " + isTokenValid, "anonymous", userIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isTokenValid) {
                    boolean isUpdated = userDao.updateUserStatusToActive(decodedToken);
                    try {
                        logDao.insertLog("INFO", "Account status update result: " + isUpdated, "anonymous", userIp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (isUpdated) {
                        try {
                            logDao.insertLog("INFO", "Account verified successfully for token: " + decodedToken, "anonymous", userIp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        response.getWriter().write("Tài khoản của bạn đã được xác thực thành công!");
                        response.sendRedirect("login");
                    } else {
                        try {
                            logDao.insertLog("ERROR", "Failed to update account status for token: " + decodedToken, "anonymous", userIp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        response.getWriter().write("Không thể xác thực tài khoản, mã xác thực không hợp lệ.");
                    }
                } else {
                    try {
                        logDao.insertLog("WARN", "Invalid token received: " + decodedToken, "anonymous", userIp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    response.getWriter().write("Mã xác thực không hợp lệ!");
                    response.sendRedirect("send-activation-link");
                }
            } catch (UnsupportedEncodingException e) {
                try {
                    logDao.insertLog("ERROR", "Error decoding token: " + tokenFromUrl, "anonymous", userIp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                response.getWriter().write("Lỗi giải mã token.");
            }
        } else {
            try {
                logDao.insertLog("WARN", "No token found in URL", "anonymous", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("Mã xác thực không hợp lệ!");
            response.sendRedirect("send-activation-link");
        }
    }}

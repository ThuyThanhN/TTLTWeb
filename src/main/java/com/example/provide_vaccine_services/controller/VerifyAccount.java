package com.example.provide_vaccine_services.controller;

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
        // Lấy token từ URL
        String tokenFromUrl = request.getParameter("token");

        // In thông tin debug về token nhận từ URL
        System.out.println("Token received from URL: " + tokenFromUrl);

        if (tokenFromUrl != null) {
            try {
                // Giải mã token từ URL
                String decodedToken = URLDecoder.decode(tokenFromUrl, "UTF-8");
                System.out.println("Decoded Token: " + decodedToken);  // In token đã giải mã để kiểm tra

                // Kiểm tra token trong cơ sở dữ liệu
                UserDao userDao = new UserDao();
                boolean isTokenValid = userDao.isTokenValid(decodedToken);  // Kiểm tra token trong cơ sở dữ liệu

                // In ra kết quả kiểm tra token trong cơ sở dữ liệu
                System.out.println("Token validity check result: " + isTokenValid);  // Kiểm tra nếu token hợp lệ

                if (isTokenValid) {
                    // Token hợp lệ, cập nhật trạng thái tài khoản
                    System.out.println("Token is valid, proceeding with status update...");

                    boolean isUpdated = userDao.updateUserStatusToActive(decodedToken);  // Cập nhật trạng thái người dùng thành "đã xác thực"

                    // In kết quả cập nhật trạng thái người dùng
                    System.out.println("Account status update result: " + isUpdated);

                    if (isUpdated) {
                        System.out.println("Account successfully verified.");
                        response.getWriter().write("Tài khoản của bạn đã được xác thực thành công!");
                        // Sau khi xác thực thành công, chuyển hướng đến trang login
                        response.sendRedirect("login");  // Chuyển hướng đến trang login
                    } else {
                        System.out.println("Error updating account status: Unable to update status.");
                        response.getWriter().write("Không thể xác thực tài khoản, mã xác thực không hợp lệ.");
                    }
                } else {
                    // Nếu token không hợp lệ
                    System.out.println("Invalid token received.");
                    response.getWriter().write("Mã xác thực không hợp lệ!");
                    response.sendRedirect("send-activation-link");  // Redirect đến trang gửi activation link nếu token không hợp lệ
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                response.getWriter().write("Lỗi giải mã token.");
            }
        } else {
            // Nếu không có token trong URL
            System.out.println("No token found in URL.");
            response.getWriter().write("Mã xác thực không hợp lệ!");
            response.sendRedirect("send-activation-link");  // Redirect đến trang gửi activation link nếu không có token
        }
    }
}

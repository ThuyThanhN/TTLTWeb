package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

@WebServlet(name = "FileDownloadServlet", value = "/uploads/*")
public class FileDownloadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "D:/uploads"; // Thư mục lưu file

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIp = request.getRemoteAddr();
        LogDao logDao = new LogDao();

        // Lấy tên file từ URL (path info)
        String fileName = request.getPathInfo();

        // Kiểm tra tên file có hợp lệ không
        if (fileName == null || fileName.equals("/")) {
            System.out.println("FileDownloadServlet: File name is missing in request from IP: " + userIp);
            try {
                logDao.insertLog("WARN", "File name is missing in download request", userIp, userIp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is missing.");
            return;
        }

        // Tạo đối tượng file dựa trên thư mục upload và tên file
        File file = new File(UPLOAD_DIR, fileName);

        // Kiểm tra file có tồn tại không
        if (!file.exists()) {
            System.out.println("FileDownloadServlet: File not found - " + file.getAbsolutePath() + " from IP: " + userIp);
            try {
                logDao.insertLog("WARN", "Requested file not found: " + fileName, userIp, userIp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        // Thiết lập content type dựa trên MIME type của file
        response.setContentType(getServletContext().getMimeType(file.getName()));

        // Thiết lập độ dài nội dung response
        response.setContentLength((int) file.length());

        // Ghi nội dung file vào response output stream
        Files.copy(file.toPath(), response.getOutputStream());

        System.out.println("FileDownloadServlet: Successfully served file - " + file.getName() + " to IP: " + userIp);
        try {
            logDao.insertLog("INFO", "File downloaded: " + fileName, userIp, userIp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không xử lý POST trong servlet này
    }
}

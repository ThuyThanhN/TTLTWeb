package com.example.provide_vaccine_services.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "FileDownloadServlet", value = "/uploads/*")
public class FileDownloadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "D:/uploads"; // Thư mục lưu file

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Lay ten file tu URL
        String fileName = request.getPathInfo();
        if (fileName == null || fileName.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is missing.");
            return;
        }

        File file = new File(UPLOAD_DIR, fileName);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setContentLength((int) file.length());

        Files.copy(file.toPath(), response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

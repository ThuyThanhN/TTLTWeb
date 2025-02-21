package com.example.provide_vaccine_services.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            byte[] hashedBytes = md.digest(passwordBytes);

            // Chuyển đổi mảng bytes thành chuỗi hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString(); // Trả về chuỗi đã hash
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu thuật toán không được hỗ trợ
            return null;
        }
    }

}

package com.example.provide_vaccine_services.Service;

import java.security.SecureRandom;

public class TokenGenerator {

    // Phương thức tạo mã token ngẫu nhiên cho việc kích hoạt tài khoản
    public static String generateActivationToken() {
        int tokenLength = 12; // Độ dài của mã token
        StringBuilder token = new StringBuilder();

        // Ký tự được phép trong mã token (bao gồm chữ cái, số và ký tự đặc biệt)
        String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_-+=<>?";

        // Tạo đối tượng SecureRandom để tạo giá trị ngẫu nhiên
        SecureRandom random = new SecureRandom();

        // Tạo mã token ngẫu nhiên
        for (int i = 0; i < tokenLength; i++) {
            int index = random.nextInt(allowedChars.length()); // Lấy chỉ số ngẫu nhiên từ chuỗi allowedChars
            char randomChar = allowedChars.charAt(index); // Lấy ký tự tại chỉ số ngẫu nhiên
            token.append(randomChar); // Thêm ký tự vào mã token
        }

        return token.toString(); // Trả về mã token
    }

}

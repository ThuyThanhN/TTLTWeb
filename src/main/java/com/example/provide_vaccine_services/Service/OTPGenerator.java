package com.example.provide_vaccine_services.Service;

import java.util.Random;

public class OTPGenerator {

    // Thời gian hết hạn của mã OTP (ví dụ 5 phút)
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút tính bằng milliseconds
    private long otpGeneratedTime; // Thời gian tạo OTP
    private String otp; // Mã OTP

    // Phương thức tĩnh để tạo mã OTP
    public static String generateOTP() {
        int otpLength = 6; // Độ dài của mã OTP
        StringBuilder otp = new StringBuilder();

        // Ký tự được phép trong mã OTP
        String allowedChars = "0123456789";

        // Tạo đối tượng Random để tạo giá trị ngẫu nhiên
        Random random = new Random();

        // Tạo mã OTP ngẫu nhiên
        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(index);
            otp.append(randomChar);
        }

        return otp.toString();
    }
}
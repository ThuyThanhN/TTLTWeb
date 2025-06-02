package com.example.provide_vaccine_services.Service.vnpay;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VerifyRecaptcha {
    private static final String SECRET_KEY = "6Leh7VIrAAAAAEg2N0jt7apVuDVmeiYcNFKneDuM"; // Khóa bí mật

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            System.out.println(">>> Lỗi: g-recaptcha-response nhận được là null hoặc rỗng.");
            return false;
        }
        try {
            System.out.println(">>> Đang gọi API xác thực reCAPTCHA của Google...");
            URL verifyUrl = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) verifyUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());
            outStream.flush();
            outStream.close();

            Scanner in = new Scanner(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
            in.close();

            System.out.println(">>> Phản hồi từ Google reCAPTCHA: " + response.toString());

            boolean success = response.toString().contains("\"success\": true");
            if (success) {
                System.out.println(">>> reCAPTCHA xác thực thành công.");
            } else {
                System.out.println(">>> reCAPTCHA xác thực thất bại.");
            }
            return success;
        } catch (Exception e) {
            System.out.println(">>> Lỗi khi gọi API xác thực reCAPTCHA:");
            e.printStackTrace();
            return false;
        }
    }
}

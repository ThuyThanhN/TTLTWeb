package com.example.provide_vaccine_services.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailSender {

    // Thông tin cấu hình email
    private static final String EMAIL_USERNAME = "22130314@st.hcmuaf.edu.vn";
    private static final String EMAIL_PASSWORD = "pmpv eycb bxqh eewc";

    // Phương thức gửi email
    public static void sendEmail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo phiên làm việc để xác thực người gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Tạo một đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Đặt thông tin người gửi, người nhận, chủ đề và nội dung email
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);

            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email to: " + toEmail);
        }
    }

//    public static void main(String[] args) {
//        // Thông tin email của người nhận
//        String toEmail = "20130404@st.hcmuaf.edu.vn";
//
//        // Chủ đề và nội dung email
//        String subject = "Test";
//        String body = "This is a test email from EmailSenderTest.";
//
//        // Gửi email
//        EmailSender.sendEmail(toEmail, subject, body);
//    }
}
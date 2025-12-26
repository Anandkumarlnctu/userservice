package com.example.onlineshopping.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("adisingh234560@gmail.com"); // must be verified in Brevo
        message.setSubject("Email Verification OTP");
        message.setText("Welcome to ECOM! Your OTP is: " + otp + "\nValid for 5 minutes.");
        mailSender.send(message);
    }
}

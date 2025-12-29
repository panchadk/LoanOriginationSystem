package com.adminplatform.los_auth.authorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("dev@yourapp.local");
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            System.out.println("Email sent to " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("no-reply@yourapp.com"); // or your Mailtrap sender

        mailSender.send(message);
    }

}
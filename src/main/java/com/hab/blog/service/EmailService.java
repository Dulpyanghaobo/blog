package com.hab.blog.service;

import com.hab.blog.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@NoArgsConstructor
public class EmailService {
    private final static String EMAIL_CONFIRMATION_SUBJECT = "Registration Confirmation";

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String email,String token) {
        // build email
        // send message
        String confirmationUrl = "/api/v1/users/register/confirm?token=" + token;

        String message = "Click to confirm" + "\r\nhttps://localhost:8081" + confirmationUrl;
        String from = "17843150270@163.com";
        send(email, from, message);
    }

    @Async
    protected void send(String to, String from, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(EMAIL_CONFIRMATION_SUBJECT);
            helper.setText(email);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
//            log.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
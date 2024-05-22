package com.hab.blog.api.v1.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Component
public class EmailService {
    private final static String EMAIL_CONFIRMATION_SUBJECT = "Registration Confirmation";

    private final JavaMailSender javaMailSender;
    private final String emailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    public EmailService(JavaMailSender javaMailSender, String emailSender) {
        this.javaMailSender = javaMailSender;
        this.emailSender = emailSender;
    }

    public void sendConfirmationEmail(String username, String email,String token) {
        sendActivationEmail(email,username, token);
    }

    public void sendActivationEmail(String to, String username, String activationCode) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("activationCode", activationCode);

        String htmlContent = templateEngine.process("register-email", context);

        sendHtmlMail(to, "Butler注册激活邮件", htmlContent);
    }

    @Async
    protected void sendHtmlMail(String to, String subject, String htmlContent) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sendEmail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }

    public static String sendEmail() {
        return "18913505701@163.com";
    }
}
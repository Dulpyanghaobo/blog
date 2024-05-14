package com.hab.blog.api.v1.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class EmailService {
    private final static String EMAIL_CONFIRMATION_SUBJECT = "Registration Confirmation";

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String username, String email,String token) {
        // build email
        // send message
        String message = buildHtmlEmailContent(username, token);
        send(email, sendEmail(), message);
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
            helper.setText(email, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }

    public static String sendEmail() {
        return "18913505701@163.com";
    }

    public static String buildHtmlEmailContent(String username, String activationCode) {
        return "<html>" +
                "<body>" +
                "<h1>尊敬的 " + username + "，</h1>" +
                "<p>您好！感谢您注册 Butler。为了完成注册流程，请输入下面的邮箱验证码：</p>" +
                "<h2>验证码：" + activationCode + "</h2>" +
                "<p>请在10分钟内输入此验证码以完成验证。请注意，此验证码仅用于当前注册流程，不要将此验证码泄露给其他人。</p>" +
                "<p>如果您没有尝试注册 Butler，请忽略此邮件。</p>" +
                "<p>感谢您对 Butler 的支持！</p>" +
                "<p>祝好，<br>Butler 团队</p>" +
                "</body>" +
                "</html>";
    }
}
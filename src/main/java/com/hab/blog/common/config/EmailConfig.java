package com.hab.blog.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring.mail") // 添加prefix明确指定配置前缀
@EnableConfigurationProperties
public class EmailConfig {

    private static final int GMAIL_SMTP_PORT = 465;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.debug}")
    private Boolean debug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set up Gmail config
        mailSender.setHost(host);
        mailSender.setPort(GMAIL_SMTP_PORT);

        // Set up email config (using udeesa email)
        mailSender.setUsername(emailSender);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", GMAIL_SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", debug);
        return mailSender;
    }

    @Bean
    public String emailSender() {
        return emailSender;
    }
}
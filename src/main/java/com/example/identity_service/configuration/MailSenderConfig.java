package com.example.minio_storage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Configuration
//Config bằng file java
public class MailSenderConfig {
    private static final String FROM_EMAIL ="quangthinh06112004@gmail.com";

    private static final String PASSWORD = "xpwa adnf fucb tkzk";

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(HOST);
        javaMailSender.setPort(PORT);
        javaMailSender.setUsername(FROM_EMAIL);
        javaMailSender.setPassword(PASSWORD);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return javaMailSender;
    }


}

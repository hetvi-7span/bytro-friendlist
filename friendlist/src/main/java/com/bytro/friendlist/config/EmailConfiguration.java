package com.bytro.friendlist.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.smtp.ssl.trust}")
    private String trust;

    @Value("${spring.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.smtp.ssl.protocols}")
    private String protocols;

    @Value("${spring.mail.smtp.starttls.enable}")
    private String enable;

    @Value("${spring.mail.smtp.debug}")
    private String debug;

    @Bean
    public JavaMailSender emailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.ssl.trust", trust);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.ssl.protocols", protocols);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.smtp.debug", debug);

        return mailSender;
    }
}

package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.service.EmailService;
import com.bytro.friendlist.shared.record.response.EmailDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    EmailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendFriendRequestMail(EmailDetails details) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(details.recipient());
            mailMessage.setText(details.msgBody());
            mailMessage.setSubject(details.subject());
            javaMailSender.send(mailMessage);
            LOG.info("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.bytro.friendlist.service;

import com.bytro.friendlist.shared.record.response.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
}

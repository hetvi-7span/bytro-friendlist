package com.bytro.friendlist.service;

import com.bytro.friendlist.shared.record.response.EmailDetails;

public interface EmailService {
    void sendFriendRequestMail(EmailDetails details);
}

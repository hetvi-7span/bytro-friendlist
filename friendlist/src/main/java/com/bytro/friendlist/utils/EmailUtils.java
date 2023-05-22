package com.bytro.friendlist.utils;

import com.bytro.friendlist.shared.record.response.EmailDetails;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private final MessageSource messageSource;

    @Value("${request.mail.subject}")
    private String mailSubject;

    @Value("${request.mail.receiver}")
    private String mailReceiver;

    public EmailUtils(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public EmailDetails createEmailTemplate(String message) {
        if (message.isEmpty()) {
            return new EmailDetails(
                    mailReceiver,
                    messageSource.getMessage(
                            "friend.request.email.message",
                            new String[] {
                                messageSource.getMessage(
                                        "friend.request.email.static.message",
                                        new String[] {},
                                        Locale.getDefault())
                            },
                            Locale.getDefault()),
                    mailSubject);
        }

        return new EmailDetails(
                mailReceiver,
                messageSource.getMessage(
                        "friend.request.email.message",
                        new String[] {message},
                        Locale.getDefault()),
                mailSubject);
    }
}

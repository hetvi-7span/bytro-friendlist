package com.bytro.friendlist.handler;

import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class FriendsHandler {

    private final FriendsService friendsService;
    private final MessageSource messageSource;

    FriendsHandler(final FriendsService friendsService, final MessageSource messageSource) {
        this.friendsService = friendsService;
        this.messageSource = messageSource;
    }

    public BaseResponse<Void> unfriend(Integer userId, Integer friendId) {
        friendsService.unfriend(userId, friendId);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage("unfriending.successful", new String[] {}, Locale.US));
    }
}

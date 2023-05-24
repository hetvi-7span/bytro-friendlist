package com.bytro.friendlist.handler;

import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.service.UserService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class FriendsHandler {

    private final FriendsService friendsService;
    private final MessageSource messageSource;

    private final UserService userService;

    FriendsHandler(
            final FriendsService friendsService,
            final MessageSource messageSource,
            final UserService userService) {
        this.friendsService = friendsService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public BaseResponse<Void> unfriend(Integer userId, Integer friendId) {
        userService.checkUser(userId);
        friendsService.unfriend(userId, friendId);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage(
                        "unfriending.successful", new String[] {}, Locale.getDefault()));
    }

    public BaseResponse<Void> block(Integer userId, Integer friendId) {
        userService.checkUser(userId);
        friendsService.block(userId, friendId);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage("block.successful", new String[] {}, Locale.getDefault()));
    }

    public BaseResponse<Void> unblock(Integer userId, Integer friendId) {
        userService.checkUser(userId);
        friendsService.unblock(userId, friendId);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage(
                        "unblock.successful", new String[] {}, Locale.getDefault()));
    }
}

package com.bytro.friendlist.rest.controller;

import com.bytro.friendlist.handler.FriendsHandler;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendsController {

    private final FriendsHandler friendsHandler;

    FriendsController(final FriendsHandler friendsHandler) {
        this.friendsHandler = friendsHandler;
    }

    @PostMapping("/unfriend/{userId}/{friendId}")
    public BaseResponse<Void> unfriend(
            @PathVariable Integer userId, @PathVariable Integer friendId) {
        return friendsHandler.unfriend(userId, friendId);
    }
}

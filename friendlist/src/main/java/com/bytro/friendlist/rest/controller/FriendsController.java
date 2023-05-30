package com.bytro.friendlist.rest.controller;

import com.bytro.friendlist.handler.FriendsHandler;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendsController {

    private final FriendsHandler friendsHandler;

    FriendsController(final FriendsHandler friendsHandler) {
        this.friendsHandler = friendsHandler;
    }

    @Operation(summary = "Unfriend a friend")
    @PostMapping("/unfriend/{userId}/{friendId}")
    public BaseResponse<Void> unfriend(
            @PathVariable Integer userId, @PathVariable Integer friendId) {
        return friendsHandler.unfriend(userId, friendId);
    }

    @Operation(summary = "Block a friend")
    @PostMapping("/block/{userId}/{friendId}")
    public BaseResponse<Void> blockFriend(
            @PathVariable Integer userId, @PathVariable Integer friendId) {
        return friendsHandler.block(userId, friendId);
    }

    @Operation(summary = "Unblock a friend")
    @PostMapping("/unblock/{userId}/{friendId}")
    public BaseResponse<Void> unBlockFriend(
            @PathVariable Integer userId, @PathVariable Integer friendId) {
        return friendsHandler.unblock(userId, friendId);
    }
}

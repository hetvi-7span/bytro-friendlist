package com.bytro.friendlist.rest.controller;

import com.bytro.friendlist.handler.FriendRequestHandler;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestController {
    private final FriendRequestHandler friendRequestHandler;

    FriendRequestController(final FriendRequestHandler friendRequestHandler) {
        this.friendRequestHandler = friendRequestHandler;
    }

    @Operation(summary = "Send friend request")
    @PostMapping("/send-friend-request")
    public BaseResponse<FriendRequestResponse> send(
            @RequestBody SendFriendRequest sendFriendRequest) {
        return friendRequestHandler.send(sendFriendRequest);
    }
}

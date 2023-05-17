package com.bytro.friendlist.rest.controller;

import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @Operation(summary = "Send friend request")
    @PostMapping("/send-friend-request")
    public BaseResponse<Void> send(@RequestBody SendFriendRequest sendFriendRequest) {
        return friendRequestService.send(sendFriendRequest);
    }
}

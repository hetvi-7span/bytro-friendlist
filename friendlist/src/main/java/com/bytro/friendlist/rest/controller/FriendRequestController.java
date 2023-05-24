package com.bytro.friendlist.rest.controller;

import com.bytro.friendlist.handler.FriendRequestHandler;
import com.bytro.friendlist.shared.record.request.AcceptRejectFriendRequest;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import com.bytro.friendlist.shared.record.response.PendingFriendRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Operation(summary = "Accepting/rejecting a friend request")
    @PostMapping("/accept-reject-friend-request")
    public BaseResponse<Void> acceptRejectFriendRequest(
            @RequestBody AcceptRejectFriendRequest acceptRejectFriendRequest) {
        return friendRequestHandler.acceptRejectFriendRequest(acceptRejectFriendRequest);
    }

    @Operation(summary = "List of friend request")
    @GetMapping("/friend-requests")
    public BaseResponse<List<PendingFriendRequestResponse>> getPendingFriendRequestList(
            @RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        return friendRequestHandler.getFriendRequestList(userId, page, size);
    }
}

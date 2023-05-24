package com.bytro.friendlist.shared.record.request;

import jakarta.validation.constraints.Min;

public record AcceptRejectFriendRequest(
        @Min(value = 1, message = "Recipient id must be grater than or equal to 1") int recipientId,
        @Min(value = 1, message = "Friend request id must be grater than or equal to 1")
                int friendRequestId,
        boolean isAccepted) {}

package com.bytro.friendlist.shared.record.request;

import jakarta.validation.constraints.Min;

public record AcceptRejectFriendRequest(
        @Min(value = 1, message = "{minimum.value.of.receipt.id}") int recipientId,
        @Min(value = 1, message = "{minimum.value.of.friendship.id}") int friendRequestId,
        boolean isAccepted) {}

package com.bytro.friendlist.shared.record.request;

import jakarta.validation.constraints.Min;

public record SendFriendRequest(
        @Min(value = 1, message = "{minimum.value.of.sender.id}") int senderId,
        @Min(value = 1, message = "{minimum.value.of.receipt.id}") int receiverId,
        String message) {}

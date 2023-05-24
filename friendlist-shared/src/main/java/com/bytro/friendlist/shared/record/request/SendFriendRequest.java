package com.bytro.friendlist.shared.record.request;

import jakarta.validation.constraints.Min;

public record SendFriendRequest(
        @Min(value = 1, message = "Sender id must be grater than or equal to 1") int senderId,
        @Min(value = 1, message = "Receiver id must be grater than or equal to 1") int receiverId,
        String message) {}

package com.bytro.friendlist.shared.record.response;

import java.time.LocalDateTime;

public record PendingFriendRequestResponse(
        Integer id, LocalDateTime createdAt, int senderId, int receiverId, String message) {}

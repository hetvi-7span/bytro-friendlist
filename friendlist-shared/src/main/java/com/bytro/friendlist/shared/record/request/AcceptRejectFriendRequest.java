package com.bytro.friendlist.shared.record.request;

public record AcceptRejectFriendRequest(
        Integer recipientId, Integer friendRequestId, boolean isAccepted) {}

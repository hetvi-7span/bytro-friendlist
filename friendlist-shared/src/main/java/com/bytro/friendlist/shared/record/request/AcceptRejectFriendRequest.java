package com.bytro.friendlist.shared.record.request;

public record AcceptRejectFriendRequest(Integer senderId, Integer receiverId, boolean isAccepted) {}

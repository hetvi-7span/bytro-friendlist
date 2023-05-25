package com.bytro.friendlist.shared.record.request;

public record SendFriendRequest(int senderId, int receiverId, String message) {}

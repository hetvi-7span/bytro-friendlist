package com.bytro.friendlist.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "User not found"),
    SOMETHING_WENT_WRONG(500, "Something went wrong"),

    // send friend request
    SENDER_RECEIVER_SAME(1001, "Sender and receiver should not be same"),
    FRIEND_REQUEST_ALREADY_SENT(1002, "Friend request already sent"),
    ALREADY_FRIENDS(1003, "You both are already friends"),
    BLOCKED_BY_USER(1004, "you are blocked by user"),
    FRIEND_REQUEST_ALREADY_RECEIVED(1005, "Friend request already received by user"),

    // approve reject request
    FRIEND_REQUEST_NOT_FOUND(2001, "Friend request not found"),

    FRIENDSHIP_DOES_NOT_EXISTS(3001, "You both are not friends");

    private final int value;
    private final String name;
}

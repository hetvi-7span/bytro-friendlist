package com.bytro.friendlist.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "Bad Request"),
    USER_NOT_FOUND(404, "User not found"),
    SOMETHING_WENT_WRONG(500, "Something went wrong"),

    // send friend request
    SENDER_RECEIVER_SAME(1001, "Sender and receiver should not be same"),
    FRIEND_REQUEST_ALREADY_SENT(1002, "Friend request already sent");

    private final int value;
    private final String name;
}

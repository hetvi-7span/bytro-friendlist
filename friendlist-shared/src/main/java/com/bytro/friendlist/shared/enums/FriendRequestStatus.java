package com.bytro.friendlist.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendRequestStatus {
    SENT("Request Sent"),
    ACCEPTED("Request Accepted"),
    REJECTED("Request Rejected");

    private final String status;
}

package com.bytro.friendlist.shared.enums;

public enum FriendRequestStatus {
    SENT("Request Sent"),
    ACCEPTED("Request Accepted"),
    REJECTED("Request Rejected");

    private final String status;

    FriendRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

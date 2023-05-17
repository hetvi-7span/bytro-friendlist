package com.bytro.friendlist.shared.enums;

public enum FriendRequestStatus {
    SENT("Request Sent"),
    ACCEPTED("Request Accepted"),
    DECLINED("Request Declined");

    private final String status;

    FriendRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

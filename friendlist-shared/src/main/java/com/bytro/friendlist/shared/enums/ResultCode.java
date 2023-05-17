package com.bytro.friendlist.shared.enums;

public enum ResultCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "Bad Request"),
    USER_NOT_FOUND(404, "User not found");

    private final int value;
    private final String name;

    ResultCode(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

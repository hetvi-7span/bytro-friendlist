package com.bytro.friendlist.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "Bad Request"),
    SOMETHING_WENT_WRONG(500, "Something went wrong");
    private final int value;
    private final String name;
}

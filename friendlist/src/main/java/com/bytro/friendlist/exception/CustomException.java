package com.bytro.friendlist.exception;

import java.io.Serial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    @Serial private static final long serialVersionUID = 1L;

    private final int errorCode;
    private final String errorMessage;
}

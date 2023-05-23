package com.bytro.friendlist.shared.record.response;

public record BaseResponse<T>(
        int resultCode,
        String resultMessage,
        Long totalItems,
        Integer totalPages,
        Integer currentPage,
        T result) {

    public BaseResponse(int resultCode, String resultMessage) {
        this(resultCode, resultMessage, null);
    }

    public BaseResponse(int resultCode, String resultMessage, T result) {
        this(resultCode, resultMessage, null, null, null, result);
    }
}

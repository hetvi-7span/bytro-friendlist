package com.bytro.friendlist.exception;

import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public BaseResponse<Object> exception(CustomException exception) {
        return new BaseResponse<>(exception.getErrorCode(), exception.getErrorMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<String> errorMessages = new ArrayList<>();
        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(error -> errorMessages.add(error.getDefaultMessage()));

        return new BaseResponse<>(
                ResultCode.BAD_REQUEST.getValue(), ResultCode.BAD_REQUEST.getName(), errorMessages);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> handleException(Exception exception) {
        return new BaseResponse<>(
                ResultCode.SOMETHING_WENT_WRONG.getValue(), exception.getMessage());
    }
}
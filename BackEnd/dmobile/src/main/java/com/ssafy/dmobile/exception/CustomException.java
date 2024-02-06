package com.ssafy.dmobile.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionType exceptionType;

    public CustomException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public String getMessage() {
        return exceptionType.getMessage();
    }

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(exceptionType.getCode());
    }
}

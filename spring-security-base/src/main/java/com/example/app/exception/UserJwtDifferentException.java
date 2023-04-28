package com.example.app.exception;

import lombok.Getter;

@Getter
public class UserJwtDifferentException extends RuntimeException{
    private int code;
    private String message;

    public UserJwtDifferentException() {super();}

    public UserJwtDifferentException(String message) {
        super(message);
    }
    public UserJwtDifferentException(ErrorCode statusCode) {
        this.code = statusCode.getStatus().value();
        this.message = statusCode.getMessage();
    }
}

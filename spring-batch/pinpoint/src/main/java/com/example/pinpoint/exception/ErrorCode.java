package com.example.pinpoint.exception;

public enum Error {

    ERROR(1000, "에러");

    private final Integer resultCode;
    private final String message;

    Error(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
}

package com.example.exceptionhandler;

import com.example.exception.CustomException;
import com.example.moduleapi.response.CommonResponse;
import com.example.modulecommon.enums.CodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public CommonResponse handlerCustomException(CustomException e) {
        return CommonResponse.builder()
                .resultCode(e.getReturnCode())
                .resultMessage(e.getReturnMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse handlerException(Exception e) {
        return CommonResponse.builder()
                .resultCode(CodeEnum.UNKNOWN_ERROR.getCode())
                .resultMessage(CodeEnum.UNKNOWN_ERROR.getMessage())
                .build();
    }
}

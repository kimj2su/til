package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindExceptionHandler(BindException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}

package com.challenge.wenance.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    private ResponseEntity<ApiError> buildResponseEntity(final ApiError apiError){
        return new ResponseEntity<ApiError>(apiError, apiError.getCode());
    }

    @ExceptionHandler( Exception.class )
    protected ResponseEntity<ApiError> handleException(Exception ex) {
        log.error(ex.getMessage());
        ApiError apiError = ApiError.builder().code(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build();
        return buildResponseEntity(apiError);
    }
}

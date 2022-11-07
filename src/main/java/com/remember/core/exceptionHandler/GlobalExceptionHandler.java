package com.remember.core.exceptionHandler;

import com.remember.core.dtos.responses.BaseResponse;
import com.remember.core.exceptions.RememberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RememberException.class)
    protected ResponseEntity<?> handleRememberException(
            RememberException e) {

        return new ResponseEntity<>(
                BaseResponse.ofErrorCode(e.getErrorCode()),
                HttpStatus.resolve(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(
            RuntimeException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(BaseResponse.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.hospital.handler;

import com.hospital.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(ResourceNotFoundException ex) {
        ApiError err = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis() / 1000L);
        return new ResponseEntity<>(err, new HttpHeaders(), err.getStatus());
    }
}

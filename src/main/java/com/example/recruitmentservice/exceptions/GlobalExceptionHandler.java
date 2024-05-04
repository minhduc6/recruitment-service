package com.example.recruitmentservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException ex) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse();
        apiExceptionResponse.setMessage(ex.getMessage());
        apiExceptionResponse.setStatus(HttpStatus.NOT_FOUND);
        apiExceptionResponse.setTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiExceptionResponse);
    }
}
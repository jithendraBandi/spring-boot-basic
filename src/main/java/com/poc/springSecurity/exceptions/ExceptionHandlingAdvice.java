package com.poc.springSecurity.exceptions;

import com.poc.springSecurity.dto.response.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorApiResponse> handleCustomException(CustomException e) {
        ErrorApiResponse response = ErrorApiResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleCustomException(ResourceNotFoundException e) {
        ErrorApiResponse response = ErrorApiResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorApiResponse> handleUnauthorizedException(BadCredentialsException e) {
        ErrorApiResponse response = ErrorApiResponse.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleUnknownException(Exception e) {
        List<String> errors = Collections.singletonList(e.getMessage());
        ErrorApiResponse response = ErrorApiResponse.builder()
                .message("Something went wrong. Please try again later!")
                .errors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

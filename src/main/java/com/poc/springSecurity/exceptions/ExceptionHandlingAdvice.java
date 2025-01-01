package com.poc.springSecurity.exceptions;

import com.poc.springSecurity.dto.response.ErrorApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorApiResponse> handleCustomException(CustomException e) {
        ErrorApiResponse response = new ErrorApiResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleCustomException(ResourceNotFoundException e) {
        ErrorApiResponse response = new ErrorApiResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorApiResponse> handleUnauthorizedException(BadCredentialsException e) {
        ErrorApiResponse response = new ErrorApiResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ErrorApiResponse response = new ErrorApiResponse("Invalid Input", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleUnknownException(Exception e) {
        List<String> errors = Collections.singletonList(e.getMessage());
        ErrorApiResponse response = new ErrorApiResponse("Something went wrong. Please try again later!", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

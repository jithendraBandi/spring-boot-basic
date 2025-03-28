package com.poc.basic.exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}

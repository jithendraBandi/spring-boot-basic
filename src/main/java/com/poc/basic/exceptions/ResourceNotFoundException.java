package com.poc.basic.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

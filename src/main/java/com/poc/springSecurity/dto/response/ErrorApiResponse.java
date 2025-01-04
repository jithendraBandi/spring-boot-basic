package com.poc.springSecurity.dto.response;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorApiResponse {

    private Date responseTimestamp = new Date();

    private String message;

    private List<String> errors = Collections.emptyList();

    public ErrorApiResponse(String message) {
        this.message = message;
    }

    public ErrorApiResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}

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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorApiResponse {

    @Builder.Default
    private Date responseTimestamp = new Date();

    private String message;

    @Builder.Default
    private List<String> errors = Collections.emptyList();

    public ErrorApiResponse(String message) {
        this.message = message;
    }

    public ErrorApiResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    @PrePersist
    public void setDefaults() {
        if (this.errors == null) {
            errors = Collections.emptyList();
        }
        this.responseTimestamp = new Date();
    }
}

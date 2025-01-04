package com.poc.springSecurity.dto.response;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Date responseTimestamp = new Date();

    private Object data;

    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }

}

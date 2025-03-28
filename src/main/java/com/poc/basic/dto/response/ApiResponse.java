package com.poc.basic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

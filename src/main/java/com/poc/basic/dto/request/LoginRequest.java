package com.poc.basic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "Username should not be empty")
    private String username;

    @NotNull(message = "Password should not be empty")
    private String password;
}

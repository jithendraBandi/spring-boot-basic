package com.poc.springSecurity.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
//    private String password;
    private List<String> roles;
    private String baseId;
}

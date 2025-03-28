package com.poc.basic.service;

import com.poc.basic.config.security.UserDetailsImpl;
import com.poc.basic.dto.request.LoginRequest;
import com.poc.basic.dto.request.UserRequest;
import com.poc.basic.dto.response.UserResponse;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UsersService {
    UserResponse addUser(UserRequest request);

    Map<String, String> getPrincipleUserDetails(UserDetailsImpl userDetailsImpl);

    String authenticateUser(LoginRequest request) throws NoSuchAlgorithmException;
}

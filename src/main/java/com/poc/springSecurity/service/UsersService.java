package com.poc.springSecurity.service;

import com.poc.springSecurity.config.securityImpl.UserDetailsImpl;
import com.poc.springSecurity.dto.request.LoginRequest;
import com.poc.springSecurity.dto.request.UserRequest;
import com.poc.springSecurity.dto.response.UserResponse;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UsersService {
    UserResponse addUser(UserRequest request);

    Map<String, String> getPrincipleUserDetails(UserDetailsImpl userDetailsImpl);

    String authenticateUser(LoginRequest request) throws NoSuchAlgorithmException;
}

package com.poc.springSecurity.service;

import com.poc.springSecurity.config.securityImpl.UserDetailsImpl;
import com.poc.springSecurity.dto.request.LoginRequest;
import com.poc.springSecurity.dto.request.UserRequest;
import org.springframework.security.web.csrf.CsrfToken;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UsersService {
    void addUser(UserRequest request);

    Map<String, String> getPrincipleUserDetails(UserDetailsImpl userDetailsImpl);

    String authenticateUser(LoginRequest request) throws NoSuchAlgorithmException;
}

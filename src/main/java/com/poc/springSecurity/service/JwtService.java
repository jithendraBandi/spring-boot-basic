package com.poc.springSecurity.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.NoSuchAlgorithmException;

public interface JwtService {
    String generateToken(String username) throws NoSuchAlgorithmException;

    String extractUsername(String jwtToken) throws NoSuchAlgorithmException;

    boolean validateToken(String jwtToken, UserDetails userDetails) throws NoSuchAlgorithmException;
}

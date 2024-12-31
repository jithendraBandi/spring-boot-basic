package com.poc.springSecurity.service;

import java.security.NoSuchAlgorithmException;

public interface JwtService {
    String generateToken(String username) throws NoSuchAlgorithmException;
}

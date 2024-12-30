package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken() {
        return "";
    }
}

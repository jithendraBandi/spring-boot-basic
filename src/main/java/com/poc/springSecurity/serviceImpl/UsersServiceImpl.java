package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.config.securityImpl.UserDetailsImpl;
import com.poc.springSecurity.dto.request.LoginRequest;
import com.poc.springSecurity.dto.request.UserRequest;
import com.poc.springSecurity.dto.response.UserResponse;
import com.poc.springSecurity.entity.Users;
import com.poc.springSecurity.mapper.CommonMapper;
import com.poc.springSecurity.repository.UsersRepository;
import com.poc.springSecurity.service.JwtService;
import com.poc.springSecurity.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public UserResponse addUser(UserRequest request) {
        Users user = CommonMapper.mapper.convertUsersRequestToEntity(request);
        usersRepository.save(user);
        return null;
    }

    @Override
    public Map<String, String> getPrincipleUserDetails(UserDetailsImpl user) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("Username", user.getUsername());
        response.put("Password", user.getPassword());
        response.put("Roles", user.getAuthorities().toString());
        return response;
    }

    @Override
    public String authenticateUser(LoginRequest request) throws NoSuchAlgorithmException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (!authentication.isAuthenticated()) throw new RuntimeException("Invalid username or password");
        return jwtService.generateToken(request.getUsername());
    }
}

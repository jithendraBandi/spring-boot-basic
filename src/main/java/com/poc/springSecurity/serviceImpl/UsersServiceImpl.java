package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.config.securityImpl.UserDetailsImpl;
import com.poc.springSecurity.dto.request.UserRequest;
import com.poc.springSecurity.entity.Users;
import com.poc.springSecurity.repository.UsersRepository;
import com.poc.springSecurity.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

//    @Autowired
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public void addUser(UserRequest request) {
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEncodedPassword(encoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        user.setBaseId(request.getBaseId());
        usersRepository.save(user);
    }

    @Override
    public Map<String, String> getPrincipleUserDetails(UserDetailsImpl user) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("Username", user.getUsername());
        response.put("Password", user.getPassword());
        response.put("Roles", user.getAuthorities().toString());
        return response;
    }
}

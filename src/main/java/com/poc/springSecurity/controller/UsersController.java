package com.poc.springSecurity.controller;

import com.poc.springSecurity.config.securityImpl.UserDetailsImpl;
import com.poc.springSecurity.dto.request.LoginRequest;
import com.poc.springSecurity.dto.request.UserRequest;
import com.poc.springSecurity.entity.Users;
import com.poc.springSecurity.repository.UsersRepository;
import com.poc.springSecurity.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserRequest request) {
        usersService.addUser(request);
        return ResponseEntity.ok("User Added Successfully.");
    }

    @GetMapping("/principle-user-details")
    public ResponseEntity<Map<String, String>> getPrincipleUserDetails(Authentication authentication) {
        //        User user = (User) authentication.getPrincipal();  // Password will be null if User type is used.
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, String> response = usersService.getPrincipleUserDetails(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest request) throws NoSuchAlgorithmException {
        String response = usersService.authenticateUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/csrf-token")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request) {
        // token visible in cookies in post man
        return ResponseEntity.ok((CsrfToken) request.getAttribute("_csrf"));
    }
}

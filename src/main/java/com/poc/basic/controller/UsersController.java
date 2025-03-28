package com.poc.basic.controller;

import com.poc.basic.config.security.UserDetailsImpl;
import com.poc.basic.dto.request.LoginRequest;
import com.poc.basic.dto.request.UserRequest;
import com.poc.basic.dto.response.ApiResponse;
import com.poc.basic.dto.response.UserResponse;
import com.poc.basic.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    // It should be available only for authenticated user with SUPER_ADMIN or HIGH_ADMINISTRATOR role only
    @PostMapping("/add-user")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserRequest request) {
        UserResponse userResponse = usersService.addUser(request);
        ApiResponse apiResponse = new ApiResponse(userResponse, "User added successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/principle-user-details")
    public ResponseEntity<ApiResponse> getPrincipleUserDetails(Authentication authentication) {
        //        User user = (User) authentication.getPrincipal();  // Password will be null if User type is used.
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, String> response = usersService.getPrincipleUserDetails(user);
        ApiResponse apiResponse = new ApiResponse(response, "User fetched successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest request) throws NoSuchAlgorithmException {
        String jwtToken = usersService.authenticateUser(request);
        ApiResponse apiResponse = new ApiResponse(jwtToken, "User logged in successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/csrf-token")
    public ResponseEntity<ApiResponse> getCsrfToken(HttpServletRequest request) {
        // token visible in cookies in post man
        // as we use sessionManagement STATELESS in securityConfig the sessionId will be changed automatically for every request
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        ApiResponse apiResponse = new ApiResponse(csrfToken, "Csrf token fetched successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}

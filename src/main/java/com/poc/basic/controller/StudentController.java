package com.poc.basic.controller;

import com.poc.basic.dto.request.StudentRequest;
import com.poc.basic.dto.response.ApiResponse;
import com.poc.basic.dto.response.StudentResponse;
import com.poc.basic.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getStudents(HttpServletRequest httpServletRequest) {

//        return ResponseEntity.ok("Greeting Hello " + httpServletRequest.getSession().getId());
        List<StudentResponse> students = studentService.getStudents();
        ApiResponse apiResponse = new ApiResponse(students, "Students fetched successfully.");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/auth")
    public ResponseEntity<String> authGetStudents() {
        return ResponseEntity.ok("Authentication successful and viewing auth content");
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addStudent(@RequestBody StudentRequest request) {
        StudentResponse studentResponse = studentService.addStudent(request);
        ApiResponse apiResponse = new ApiResponse(studentResponse, "Student added successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}

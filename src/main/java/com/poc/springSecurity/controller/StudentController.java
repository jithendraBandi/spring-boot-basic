package com.poc.springSecurity.controller;

import com.poc.springSecurity.dto.request.StudentRequest;
import com.poc.springSecurity.dto.response.StudentResponse;
import com.poc.springSecurity.entity.Student;
import com.poc.springSecurity.repository.StudentRepository;
import com.poc.springSecurity.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<StudentResponse>> getStudents(HttpServletRequest httpServletRequest) {

//        return ResponseEntity.ok("Greeting Hello " + httpServletRequest.getSession().getId());
        List<StudentResponse> students = studentService.getStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("")
    public ResponseEntity<String> addStudent(@RequestBody StudentRequest request) {
        studentService.addStudent(request);
        return ResponseEntity.ok("Student added successfully.");
    }
}

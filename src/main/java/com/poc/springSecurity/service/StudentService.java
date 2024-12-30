package com.poc.springSecurity.service;

import com.poc.springSecurity.dto.request.StudentRequest;
import com.poc.springSecurity.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getStudents();

    void addStudent(StudentRequest request);
}

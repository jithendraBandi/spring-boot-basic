package com.poc.basic.service;

import com.poc.basic.dto.request.StudentRequest;
import com.poc.basic.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getStudents();

    StudentResponse addStudent(StudentRequest request);
}

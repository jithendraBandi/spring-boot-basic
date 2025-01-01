package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.dto.request.StudentRequest;
import com.poc.springSecurity.dto.response.StudentResponse;
import com.poc.springSecurity.entity.Student;
import com.poc.springSecurity.mapper.CommonMapper;
import com.poc.springSecurity.repository.StudentRepository;
import com.poc.springSecurity.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<StudentResponse> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(CommonMapper.mapper::convertStudentEntityToRes).toList();
    }

    @Override
    public void addStudent(StudentRequest request) {
        Student student = CommonMapper.mapper.convertStudentRequestToEntity(request);
        studentRepository.save(student);
    }
}

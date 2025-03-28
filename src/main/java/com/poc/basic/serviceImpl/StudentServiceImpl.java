package com.poc.basic.serviceImpl;

import com.poc.basic.dto.request.StudentRequest;
import com.poc.basic.dto.response.StudentResponse;
import com.poc.basic.entity.Student;
import com.poc.basic.mapper.CommonMapper;
import com.poc.basic.repository.StudentRepository;
import com.poc.basic.service.StudentService;
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
    public StudentResponse addStudent(StudentRequest request) {
        Student student = CommonMapper.mapper.convertStudentRequestToEntity(request);
        studentRepository.save(student);
        return CommonMapper.mapper.convertStudentEntityToRes(student);
    }
}

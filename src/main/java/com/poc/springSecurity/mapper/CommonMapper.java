package com.poc.springSecurity.mapper;

import com.poc.springSecurity.dto.request.StudentRequest;
import com.poc.springSecurity.dto.request.UserRequest;
import com.poc.springSecurity.dto.response.StudentResponse;
import com.poc.springSecurity.entity.Student;
import com.poc.springSecurity.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommonMapper {
    CommonMapper mapper = Mappers.getMapper(CommonMapper.class);

    Student convertStudentRequestToEntity(StudentRequest request);
    StudentResponse convertStudentEntityToRes(Student student);

    Users convertUsersRequestToEntity(UserRequest request);

//    @Mapping(target = "categoryName",expression = "java(request.getCategoryName().toUpperCase())")
//    @Mapping(target = "components",ignore = true)
}

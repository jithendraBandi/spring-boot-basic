package com.poc.basic.mapper;

import com.poc.basic.dto.request.StudentRequest;
import com.poc.basic.dto.request.UserRequest;
import com.poc.basic.dto.response.StudentResponse;
import com.poc.basic.entity.Student;
import com.poc.basic.entity.Users;
import org.mapstruct.Mapper;
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

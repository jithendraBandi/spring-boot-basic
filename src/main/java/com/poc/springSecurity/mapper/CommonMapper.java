//package com.poc.springSecurity.mapper;
//
//import com.poc.springSecurity.dto.response.StudentResponse;
//import com.poc.springSecurity.entity.Student;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface CommonMapper {
//    CommonMapper mapper = Mappers.getMapper(CommonMapper.class);
//
//    StudentResponse convertStudentEntityToRes(Student student);
//
////    @Mapping(target = "categoryName",expression = "java(request.getCategoryName().toUpperCase())")
////    @Mapping(target = "components",ignore = true)
//}

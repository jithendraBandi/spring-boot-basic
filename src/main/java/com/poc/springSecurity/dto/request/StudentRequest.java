package com.poc.springSecurity.dto.request;

import lombok.Data;

@Data
public class StudentRequest {
    private String name;
    private Integer age;
    private String baseId;
}

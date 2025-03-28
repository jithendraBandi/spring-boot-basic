package com.poc.basic.dto.response;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private Integer age;
    private String baseId;
}

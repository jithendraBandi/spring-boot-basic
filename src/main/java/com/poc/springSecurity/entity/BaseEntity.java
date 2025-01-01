package com.poc.springSecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {
    @Column(name = "BASE_ID")
    private String baseId;
}

package com.poc.springSecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poc.springSecurity.converter.StringListConvertor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "USER_CREDENTIALS")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENCODED_PASSWORD")
    private String encodedPassword;

    @Convert(converter = StringListConvertor.class)
    @Column(name = "ROLES", columnDefinition = "TEXT")
    private List<String> roles;

    @PrePersist
    public void setDefaults() {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = Collections.singletonList("USER");
        }
    }
}

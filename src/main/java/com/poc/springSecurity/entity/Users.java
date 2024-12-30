package com.poc.springSecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poc.springSecurity.converter.StringListConvertor;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

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

    public Users() {}

    public Users(Long id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}

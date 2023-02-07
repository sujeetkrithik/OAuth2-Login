package com.example.entity;

import com.example.dto.AuthenticationProvider;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String mobile;
    private String mail;
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider auth_provider;
}

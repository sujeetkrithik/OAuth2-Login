package com.example.entity;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String mobile;
    private String mail;
    private String password;
}

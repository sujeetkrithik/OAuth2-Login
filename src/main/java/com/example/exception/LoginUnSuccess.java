package com.example.exception;

public class LoginUnSuccess extends RuntimeException{
    public LoginUnSuccess(String message){
        super(message);
    }
}


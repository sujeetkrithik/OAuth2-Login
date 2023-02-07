package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        Response response =new Response();
        response.setMessage(e.getMessage());
        response.setStatus("Bad request");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

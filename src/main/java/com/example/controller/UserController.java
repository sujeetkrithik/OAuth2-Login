package com.example.controller;

import com.example.entity.LoginRequest;
import com.example.entity.UserRequest;
import com.example.entity.UserResponse;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/user/register")
    public UserResponse register(@RequestBody UserRequest request){
        return service.registerUser(request);
    }

    @GetMapping("/user/{id}")
    public UserResponse getById(@PathVariable int id){
        return service.getById(id);
    }

    @GetMapping("/user")
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers();
    }

//    @PostMapping("/user/{id}")
//    public UserResponse addAddress(@RequestBody List<Address> address, @PathVariable int id){
//        return  service.addAddress(address,id);
//    }

    @PostMapping("/user/login")
    public Object login(@RequestBody LoginRequest request){
        return service.validateLogin(request);
    }

    @GetMapping("/")
    public void redirectToUser(HttpServletResponse response) throws IOException {
        response.sendRedirect("/user");
    }
}

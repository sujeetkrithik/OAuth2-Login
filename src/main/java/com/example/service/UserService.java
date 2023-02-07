package com.example.service;

import com.example.dto.AuthenticationProvider;
import com.example.entity.*;
import com.example.exception.LoginUnSuccess;
import com.example.exception.UserNotFound;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final String AUTHORIZED="Authorized";
    public static final String USER_NOT_FOUND="User not present with id : ";
    public static final String UNAUTHORIZED="Invalid username/password";
    public static final String MAIL_ALREADY_EXISTS="Mail id already exists";
    public static final String MOBILE_ALREADY_EXISTS="Mobile no: already exists";

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse registerUser(UserRequest request){
        User user=new User();
        String name= request.getFirstName()+" "+request.getLastName();
        user.setName(name);
        if(userRepository.existsByMobile(request.getMobile())){
            throw new RuntimeException(MOBILE_ALREADY_EXISTS);
        }
        user.setMobile(request.getMobile());
        if(userRepository.existsByMail(request.getMail())){
            throw new RuntimeException(MAIL_ALREADY_EXISTS);
        }
        user.setMail(request.getMail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        UserResponse userResponse=new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setMobile(savedUser.getMobile());
        userResponse.setMail(savedUser.getMail());
//        List<Address> addresses = savedUser.getAddresses();
//        List<String> addressResponse=new ArrayList<>();
//        if(addresses!=null) {
//            for (Address address : addresses) {
//                addressResponse.add(address.getAddress());
//            }
//            userResponse.setAddress(addressResponse);
//        }
        return userResponse;
    }

    public UserResponse getById(int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new UserNotFound(USER_NOT_FOUND+id);
        }
        User user = byId.get();
        UserResponse response=new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setMobile(user.getMobile());
        response.setMail(user.getMail());
//        List<Address> addresses = user.getAddresses();
//        List<String> addressResponse=new ArrayList<>();
//        for(Address address:addresses){
//            addressResponse.add(address.getAddress());
//        }
//        response.setAddress(addressResponse);
        return response;
    }

    public List<UserResponse> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        List<UserResponse> userResponses=new ArrayList<>();
        for(User user:allUsers){
            UserResponse response=new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setMobile(user.getMobile());
            response.setMail(user.getMail());
//            List<Address> addresses = user.getAddresses();
//            List<String> addressResponse=new ArrayList<>();
//            if(addresses!=null) {
//                for (Address address : addresses) {
//                    addressResponse.add(address.getAddress());
//                }
//                response.setAddress(addressResponse);
//            }
            userResponses.add(response);
        }
        return userResponses;
    }

//    public UserResponse addAddress(List<Address> address,int id){
//        Optional<User> byId = userRepository.findById(id);
//        if(byId.isEmpty()){
//            throw new UserNotFound(USER_NOT_FOUND+id);
//        }
//        User user = byId.get();
//        List<Address> addresses=null;
//        if(user.getAddresses()!=null){
//            addresses = user.getAddresses();
//        }else {
//            addresses=new ArrayList<>();
//        }
//
//        addresses.addAll(address);
//
//        user.setAddresses(addresses);
//        User savedUser=userRepo.save(user);
//        UserResponse userResponse=new UserResponse();
//        userResponse.setId(savedUser.getId());
//        userResponse.setName(savedUser.getName());
//        userResponse.setMobile(savedUser.getMobile());
//        userResponse.setMail(savedUser.getMail());
//        List<Address> addressesSaved = savedUser.getAddresses();
//        List<String> addressResponse=new ArrayList<>();
//        for(Address i:addressesSaved){
//            addressResponse.add(i.getAddress());
//        }
//        userResponse.setAddress(addressResponse);
//
//        return userResponse;
//    }

    public Object validateLogin(LoginRequest request){
        String password = request.getPassword();
        String username = request.getUsername();
        if(userRepository.existsByMail(username) || userRepository.existsByMobile(username)){
            User user=null;
            if(userRepository.existsByMail(username)){
                user=userRepository.findByMail(username);
            }
            else {
                user=userRepository.findByMobile(username);
            }
            String password1 = user.getPassword();
            if(password.equals(password1)) {
                AuthorizedLogin authorizedLogin=new AuthorizedLogin();
                authorizedLogin.setStatus(AUTHORIZED);
                authorizedLogin.setName(user.getName());
                return authorizedLogin;
            }
            else throw new LoginUnSuccess(UNAUTHORIZED);
        }else {
            throw new LoginUnSuccess(UNAUTHORIZED);
        }
    }

    public void processOauthPostLogin(String email,String name){
        User user = userRepository.findByMail(email);
        if(user==null){
            User user1=new User();
            user1.setName(name);
            user1.setMail(email);
            user1.setAuth_provider(AuthenticationProvider.GOOGLE);
            userRepository.save(user1);
        }
        else{
            user.setName(name);
            user.setAuth_provider(AuthenticationProvider.GOOGLE);
            userRepository.save(user);
        }
        if(user==null){
            User user1=new User();
            user1.setName(name);
            user1.setMail(email);
            user1.setAuth_provider(AuthenticationProvider.FACEBOOK);
            userRepository.save(user1);
        }

        else{
            user.setName(name);
            user.setAuth_provider(AuthenticationProvider.FACEBOOK);
            userRepository.save(user);
        }

        }
    }

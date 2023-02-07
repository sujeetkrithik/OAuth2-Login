package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.service.UserService.UNAUTHORIZED;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        if(userRepository.existsByMail(username) || userRepository.existsByMobile(username)){
            if(userRepository.existsByMail(username)){
                user=userRepository.findByMail(username);
            }
            else {
                user=userRepository.findByMobile(username);
            }
        }else {
            throw new UsernameNotFoundException(UNAUTHORIZED);
        }
        MyUserPrinciple myUserPrinciple=new MyUserPrinciple(userRepository);
        myUserPrinciple.setUser(user);
        myUserPrinciple.setUsername(username);
        return myUserPrinciple;
    }
}

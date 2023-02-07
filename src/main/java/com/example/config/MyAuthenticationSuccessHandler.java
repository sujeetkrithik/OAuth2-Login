package com.example.config;

import com.example.service.CustomOauth2User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOauth2User customOauth2User = (CustomOauth2User) authentication.getPrincipal();
        String email = customOauth2User.getEmail();
        String name = customOauth2User.getName();
        userService.processOauthPostLogin(email, name);

        super.onAuthenticationSuccess(request, response, authentication);

    }
}

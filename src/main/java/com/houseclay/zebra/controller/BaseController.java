package com.houseclay.zebra.controller;

import com.houseclay.zebra.utils.ApplicationConfig;
import com.houseclay.zebra.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BaseController {

    private final ApplicationConfig applicationConfig;

    public BaseController() {
        applicationConfig = new ApplicationConfig();
    }

    public String findUsernameFromHeader(String token){
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils(applicationConfig);
        String username = jwtTokenUtils.extractUsernamefromToken(token);
        System.out.println("Logged in user::::: "+username);
        return username;
    }

    public List<String> extractRoleFromToken(String token){
        JwtTokenUtils jwtTokenUtils=new JwtTokenUtils(applicationConfig);
        List<String> roles = jwtTokenUtils.extractRoleFromToken(token);
        System.out.println("Logged in user Role :::: "+roles);
        return roles;
    }

}

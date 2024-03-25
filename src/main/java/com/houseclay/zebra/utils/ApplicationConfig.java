package com.houseclay.zebra.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${secretKey}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}

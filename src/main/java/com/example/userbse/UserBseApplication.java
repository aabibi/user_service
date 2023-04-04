package com.example.userbse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserBseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBseApplication.class, args);
    }

}

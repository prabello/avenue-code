package com.avenuecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AvenueApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvenueApplication.class, args);
    }
}

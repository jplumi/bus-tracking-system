package com.jplumi.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PassengerServiceApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassengerServiceApplication.class, args);
    }
}
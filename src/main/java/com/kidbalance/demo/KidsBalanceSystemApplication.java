package com.kidbalance.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KidsBalanceSystemApplication {

    public static void main(String[] args) {
        // This launches the entire Spring context, connecting the DB and starting your CLI
        SpringApplication.run(KidsBalanceSystemApplication.class, args);
    }
}
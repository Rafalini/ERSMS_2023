package com.ersms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ersms.app")
@EnableJpaRepositories("com.ersms.app")
public class DataService {
    public static void main(String[] args) {
        SpringApplication.run(DataService.class, args);
    }
}
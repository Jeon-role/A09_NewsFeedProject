package com.example.newsfeedproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class NewsFeedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsFeedProjectApplication.class, args);
    }

}

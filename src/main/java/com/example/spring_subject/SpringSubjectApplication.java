package com.example.spring_subject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringSubjectApplication {
    public static void main(String[] args) {
        SpringApplication.run ( SpringSubjectApplication.class, args );
    }

}



package com.spatra.curtain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CurtainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurtainApplication.class, args);
    }

}

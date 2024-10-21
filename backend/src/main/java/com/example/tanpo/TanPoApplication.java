package com.example.tanpo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class TanPoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanPoApplication.class, args);
    }

}

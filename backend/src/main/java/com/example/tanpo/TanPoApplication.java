package com.example.tanpo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableRabbit
@EnableCaching  // 캐시 활성화 어노테이션 추가
public class TanPoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanPoApplication.class, args);
    }

}


package com.example.tanpo.config;

import com.example.tanpo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 관련 Bean 등록 설정
 * - application.properties의 app.jwt.* 값을 주입받아 JwtUtil을 생성한다.
 * - JwtUtil에 @Component가 달려있다면 제거해야 중복 빈 문제가 없다.
 */
@Configuration
public class JwtBeansConfig {

    @Bean
    public JwtUtil jwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expires.minutes:60}") long expiresMinutes
    ) {
        return new JwtUtil(secret, expiresMinutes);
    }
}


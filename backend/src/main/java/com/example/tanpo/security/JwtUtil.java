package com.example.tanpo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWT 발급/검증 유틸
 * - 생성자에서 secret, 만료시간(분) 주입
 * - issue(userId, kakaoId), verify(token) 제공
 */
public class JwtUtil {

    private final Algorithm algorithm;
    private final long expiresMinutes;

    public JwtUtil(String secret, long expiresMinutes) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expiresMinutes = expiresMinutes;
    }

    public String issue(Long userId, Long kakaoId) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer("tanpo")
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(expiresMinutes, ChronoUnit.MINUTES)))
                .withSubject(String.valueOf(userId))   // sub = 내부 PK
                .withClaim("kid", kakaoId)            // kakao id (선택)
                .withClaim("provider", "kakao")
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        return JWT.require(algorithm)
                .withIssuer("tanpo")
                .build()
                .verify(token);
    }
}



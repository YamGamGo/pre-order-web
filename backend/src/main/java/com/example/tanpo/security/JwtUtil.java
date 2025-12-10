package com.example.tanpo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.time.Instant;
import java.util.Date;

/**
 * JWT 발급 & 검증 유틸리티
 * - issue() : JWT 토큰 발급
 * - verify() : JWT 토큰 검증 및 DecodedJWT 반환
 */
public class JwtUtil {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long expiresMinutes;

    public JwtUtil(String secret, long expiresMinutes) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer("tanpo").build();
        this.expiresMinutes = expiresMinutes;
    }

    /**
     * JWT 발급
     * @param userId   내부 DB PK
     * @param kakaoId  카카오 사용자 ID
     * @return JWT 문자열
     */
    public String issue(Long userId, Long kakaoId) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expiresMinutes);

        return JWT.create()
                .withIssuer("tanpo")               // 발급자
                .withIssuedAt(Date.from(now))      // 발급 시각
                .withExpiresAt(Date.from(exp))     // 만료 시각
                .withSubject(String.valueOf(userId)) // 내부 DB 유저 PK
                .withClaim("kid", kakaoId)         // kakao ID
                .withClaim("provider", "kakao")    // provider 정보
                .sign(algorithm);
    }

    /**
     * JWT 검증
     * @param token JWT 문자열
     * @return DecodedJWT (sub, claim 등 읽기 가능)
     */
    public DecodedJWT verify(String token) {
        return verifier.verify(token);
    }
}

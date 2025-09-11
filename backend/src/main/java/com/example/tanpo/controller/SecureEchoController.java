package com.example.tanpo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * JWT 보호 API 예시
 * - Authorization: Bearer <JWT> 없으면 401 반환
 * - 있으면 필터에서 세팅한 principal(userId) 값으로 응답
 */
@RestController
@RequestMapping("/api/protected")
public class SecureEchoController {

    /**
     * JWT 필터가 SecurityContext에 저장한 인증 객체(Authentication)를 그대로 주입받는다.
     * principal → JwtAuthenticationFilter에서 설정한 userId
     */
    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok("로그인된 사용자 ID: " + userId);
    }
}

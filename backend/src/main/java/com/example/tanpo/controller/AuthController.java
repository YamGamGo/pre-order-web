package com.example.tanpo.controller;

import com.example.tanpo.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // ✅ 쿠키 이름 (발급/삭제 시 공통으로 사용)
    private static final String COOKIE_NAME = "ACCESS_TOKEN";

    private final JwtUtil jwtUtil;
    private final long expiresMinutes;  // JWT 만료 시간 (분 단위)
    private final boolean secureCookie; // HTTPS 환경 여부에 따라 Secure 속성 적용

    public AuthController(
            JwtUtil jwtUtil,
            @Value("${app.jwt.expires.minutes:60}") long expiresMinutes, // 기본값 60분
            @Value("${app.cookie.secure:false}") boolean secureCookie    // 개발 환경에서는 false
    ) {
        this.jwtUtil = jwtUtil;
        this.expiresMinutes = expiresMinutes;
        this.secureCookie = secureCookie;
    }

    /**
     * ✅ 교환 엔드포인트
     * - 세션에 저장된 사용자 정보(USER_ID, KAKAO_ID)를 가져와 JWT 발급
     * - 발급된 JWT를 HttpOnly 쿠키(ACCESS_TOKEN)로 내려줌
     * - 이후 요청에서는 클라이언트가 이 쿠키를 자동 전송
     */
    @GetMapping("/exchange")
    public ResponseEntity<Map<String, Object>> exchange(HttpSession session,
                                                        HttpServletResponse response) {
        // 세션에서 유저 정보 꺼내오기
        Long userId  = (Long) session.getAttribute("USER_ID");
        Long kakaoId = (Long) session.getAttribute("KAKAO_ID");

        // 세션에 사용자 정보가 없으면 401 반환
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        // JWT 발급 (sub = userId, claim = kakaoId 포함)
        String jwt = jwtUtil.issue(userId, kakaoId);

        // ✅ HttpOnly 쿠키 생성
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwt)
                .httpOnly(true)                          // JS에서 접근 불가 (보안)
                .secure(secureCookie)                    // HTTPS 환경에서만 전송
                .sameSite("Lax")                         // 크로스 사이트 요청 방지
                .path("/")                               // 모든 경로에 적용
                .maxAge(expiresMinutes * 60)             // 만료시간 (초 단위)
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // ✅ 클라이언트용 응답 데이터 (디버깅/정보용)
        Map<String, Object> body = new HashMap<>();
        body.put("issued", true);
        body.put("tokenType", "Cookie");
        body.put("cookieName", COOKIE_NAME);
        body.put("expiresIn", expiresMinutes * 60);
        body.put("issuedAt", Instant.now().toString());

        return ResponseEntity.ok(body);
    }

    /**
     * ✅ 로그아웃
     * - ACCESS_TOKEN 쿠키를 빈 값("")으로 재발급하면서 maxAge=0으로 설정
     * - 브라우저에서 해당 쿠키가 즉시 삭제됨
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie delete = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // 쿠키 즉시 만료
                .build();

        // 쿠키 삭제 응답 전송
        response.addHeader(HttpHeaders.SET_COOKIE, delete.toString());
        return ResponseEntity.ok().build();
    }
}

package com.example.tanpo.controller;

import com.example.tanpo.entity.KakaoUserEntity;
import com.example.tanpo.security.JwtUtil;
import com.example.tanpo.service.KakaoLoginService;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController
 *
 * - GET /auth/kakao/login
 *   → 카카오 로그인 동의 화면으로 리다이렉트
 *
 * - GET /auth/kakao/login
 *   → 카카오 인증 후 code 수신 → AccessToken 요청 → 프로필 조회
 *   → DB upsert → JWT 발급 → 쿠키 저장 → 프론트 리다이렉트
 *
 * - GET /api/auth/jwt/exchange
 *   → JWT 재발급 (쿠키 만료 시, 세션 제거했으므로 필요 없을 수 있음)
 *
 * - POST /api/auth/jwt/logout
 *   → JWT 쿠키 제거
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final KakaoLoginService kakaoLoginService;
    private final JwtUtil jwtUtil;

    @Value("${app.front.redirect:http://localhost:3000/}")
    private String frontRedirectUrl;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // 이름 expiredSeconds 로 고치고 60*60
    @Value("${app.jwt.expires.minutes:60}")
    private long jwtExpiresMinutes;

    @Value("${app.cookie.secure:false}")
    private boolean secureCookie;

    private static final String COOKIE_NAME = "ACCESS_TOKEN";

    /**
     * 카카오 콜백: code → AccessToken → 프로필 → DB 저장 → JWT 쿠키 발급 → 프론트 리다이렉트
     *
     */
    @GetMapping("/kakao/loginpage")
        public ResponseEntity<Void> kakaologin(
                @RequestParam("code") String code,
                HttpServletResponse response
    ) {
        // 1. 인증 코드로 AccessToken 요청
        JsonObject tokenJson = kakaoLoginService.getAccessToken(code);
        if (tokenJson == null || !tokenJson.has("access_token") || tokenJson.get("access_token").isJsonNull()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

        // 2. 토큰 추출
        String accessToken = tokenJson.get("access_token").getAsString();

        // 3. xxxxxxxxx 토큰으로 카카오 프로필 조회
        JsonObject profileJson = kakaoLoginService.getKakaoProfile(accessToken);


        KakaoUserEntity savedUser = kakaoLoginService.upsertUser(tokenJson, profileJson);

        //4-1 조회
        // 4-1-a 조회 해서 있으면 로그인 그정보 그대로 반환
        // 4-1-b 없으면 인설트 하고 그정보 반환

        // 5. JWT 발급 (userId + kakaoId 기반)
        JwtUtil jwtUtil = new JwtUtil(jwtSecret, jwtExpiresMinutes);
        String jwt = jwtUtil.issue(savedUser.getId(), savedUser.getKakaoId());

        // 6. JWT를 HttpOnly 쿠키로 저장
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwt)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(jwtExpiresMinutes))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 7. 프론트엔드로 리다이렉트 잘 내려왔으면 200 문제가 있으면 로그인이 되지않음 출력
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromUriString(frontRedirectUrl).build(true).toUri());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * JWT 재발급 (선택적, 세션 제거했으면 필요 없을 수 있음)
     */
    @GetMapping("/jwt/exchange")
    public ResponseEntity<Map<String, Object>> exchange(HttpServletResponse response) {
        // JWT는 콜백에서 이미 발급 → 여기서는 재발급 용도
        String dummyJwt = jwtUtil.issue(-1L, -1L);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, dummyJwt)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(jwtExpiresMinutes))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        Map<String, Object> body = new HashMap<>();
        body.put("issued", true);
        body.put("tokenType", "Cookie");
        body.put("cookieName", COOKIE_NAME);
        body.put("expiresIn", jwtExpiresMinutes * 60);
        body.put("issuedAt", Instant.now().toString());

        return ResponseEntity.ok(body);
    }

    /**
     * 로그아웃: JWT 쿠키 제거
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie delete = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, delete.toString());
        return ResponseEntity.ok().build();
    }
}

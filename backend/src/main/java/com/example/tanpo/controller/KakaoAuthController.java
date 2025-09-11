package com.example.tanpo.controller;

import com.example.tanpo.entity.KakaoUserEntity;
import com.example.tanpo.security.JwtUtil;
import com.example.tanpo.service.KakaoLoginService;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

/**
 * KakaoAuthController
 *
 * - GET  /oauth/kakao/login       : 카카오 동의 화면으로 리다이렉트
 * - GET  /login/kakao/callback    : 카카오 인증 콜백 → DB 저장 → 세션 → JWT 발급 → 쿠키 저장 → 프론트 리다이렉트
 * - POST /api/kakao/logout        : 세션 무효화 + 쿠키 제거
 */
@RestController
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${app.front.redirect:http://localhost:3000/}")
    private String frontRedirectUrl;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    // JWT 설정값
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expires.minutes:60}")
    private long jwtExpiresMinutes;

    private static final String COOKIE_NAME = "ACCESS_TOKEN"; // ✅ 통일

    /**
     * 카카오 동의 화면으로 리다이렉트
     */
    @GetMapping("/oauth/kakao/login")
    public ResponseEntity<Void> kakaoLoginRedirect() {
        String scope = "profile_nickname,profile_image";

        String authUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&scope=" + scope;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromUriString(authUrl).build(true).toUri());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * 카카오 콜백: 토큰 발급 → 프로필 조회 → DB 저장 → 세션 저장 → JWT 쿠키 발급 → 프론트 리다이렉트
     */
    @GetMapping("/login/kakao/callback")
    public ResponseEntity<Void> kakaoCallback(
            @RequestParam("code") String code,
            HttpSession session,
            HttpServletResponse response
    ) {
        JsonObject tokenJson = kakaoLoginService.getAccessToken(code);
        if (tokenJson == null || !tokenJson.has("access_token") || tokenJson.get("access_token").isJsonNull()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

        String accessToken = tokenJson.get("access_token").getAsString();
        JsonObject profileJson = kakaoLoginService.getKakaoProfile(accessToken);

        KakaoUserEntity savedUser = kakaoLoginService.upsertUser(tokenJson, profileJson);
        session.setAttribute("USER_ID", savedUser.getId());
        session.setAttribute("KAKAO_ID", savedUser.getKakaoId());

        JwtUtil jwtUtil = new JwtUtil(jwtSecret, jwtExpiresMinutes);
        String jwt = jwtUtil.issue(savedUser.getId(), savedUser.getKakaoId());

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwt)
                .httpOnly(true)
                .secure(false) // prod 환경에서는 true 권장
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(jwtExpiresMinutes))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromUriString(frontRedirectUrl).build(true).toUri());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * 로그아웃: 세션 무효화 + 쿠키 제거
     */
    @PostMapping("/api/kakao/logout")
    public void logout(HttpSession session, HttpServletResponse response) {
        try {
            session.invalidate();
        } catch (Exception ignored) {}

        ResponseCookie remove = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // 즉시 만료
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, remove.toString());
    }
}

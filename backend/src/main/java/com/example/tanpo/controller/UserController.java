package com.example.tanpo.controller;

import com.example.tanpo.entity.KakaoUserEntity;
import com.example.tanpo.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * ✅ 현재 로그인한 사용자 정보 조회
 * - 클라이언트는 /api/user/me 호출
 * - JwtAuthenticationFilter가 SecurityContext에 넣어둔 userId 기반으로 DB 조회
 * - 로그인 상태면 사용자 정보, 아니면 loggedIn: false 반환반
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoUserRepository kakaoUserRepository;

    @GetMapping("/api/user/me") // ✅ 엔드포인트 변경 (/api/kakao/me → /api/user/me)
    public ResponseEntity<Map<String, Object>> me() {
        Map<String, Object> result = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            result.put("loggedIn", false);
            result.put("nickname", null);
            result.put("profileImage", null);
            return ResponseEntity.ok(result);
        }

        try {
            Long userId = Long.valueOf(authentication.getPrincipal().toString());
            KakaoUserEntity user = kakaoUserRepository.findById(userId).orElse(null);

            if (user == null) {
                result.put("loggedIn", false);
                result.put("nickname", null);
                result.put("profileImage", null);
                return ResponseEntity.ok(result);
            }

            result.put("loggedIn", true);
            result.put("nickname", user.getNickname());
            result.put("profileImage", user.getProfileImage());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.put("loggedIn", false);
            result.put("nickname", null);
            result.put("profileImage", null);
            return ResponseEntity.ok(result);
        }
    }
}

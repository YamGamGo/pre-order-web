package com.example.tanpo.controller;

import com.example.tanpo.entity.KakaoUserEntity;
import com.example.tanpo.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final KakaoUserRepository kakaoUserRepository;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile() {
        Map<String, Object> result = new HashMap<>();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            result.put("loggedIn", false);
            return ResponseEntity.ok(result);
        } //로그인을 하지 않았으면 == 값이 없으니 false로 응답

        String userIdStr = authentication.getPrincipal().toString();//userId 를 꺼냄
        Long userId;
        try {
            userId = Long.parseLong(userIdStr); //userIdStr는 문자열이므로, Long.parseLong()으로 Long 타입 숫자로 바꿈
        } catch (NumberFormatException e) {
            result.put("loggedIn", false);
            return ResponseEntity.ok(result);
        }

        KakaoUserEntity user = kakaoUserRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("loggedIn", false);
            return ResponseEntity.ok(result);
        }

        result.put("loggedIn", true);
        result.put("id", user.getId());
        result.put("kakaoId", user.getKakaoId());
        result.put("nickname", user.getNickname());
        result.put("profileImage", user.getProfileImage());

        return ResponseEntity.ok(result);
    }
}

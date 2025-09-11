package com.example.tanpo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 세션 보조용 컨트롤러 (현재 매핑 없음)
 * - /api/kakao/me  : KakaoMeController 가 담당
 * - /api/kakao/logout : KakaoLogoutController 가 담당
 */
@RestController
@RequestMapping("/api/kakao/session")
public class KakaoSessionController {
    // 매핑 비움 (충돌 방지)
}

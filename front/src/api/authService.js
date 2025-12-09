// src/api/authService.js
import api from "./axiosInstance";

/**
 * 카카오 로그인 시작 (프론트에서 바로 카카오 페이지로 이동)
 * - 카카오 인증 서버(https://kauth.kakao.com/oauth/authorize)로 직접 이동
 */
export function loginWithKakao() {
    const REST_API_KEY = "4788bc35a839d8b4df6ed7a44415db93"; // 카카오 REST API 키
    const REDIRECT_URI = "http://localhost:8080/api/auth/kakao/loginpage"; // 카카오 개발자 콘솔에 등록된 Redirect URI http://localhost:3000/authVerify
    const scope = "profile_nickname,profile_image"; // 동의 항목 스코프

    const kakaoAuthUrl =
        `https://kauth.kakao.com/oauth/authorize?response_type=code` +
        `&client_id=${REST_API_KEY}` +
        `&redirect_uri=${encodeURIComponent(REDIRECT_URI)}` +
        `&scope=${scope}`;

    window.location.href = kakaoAuthUrl;
}

/**
 * JWT 교환 (쿠키 발급)
 * - 서버 AuthController의 /api/auth/jwt/e43change 호출
 * - JWT를 새로 발급받아 Set-Cookie 헤더로 내려줌
 */
export async function exchangeJwt() {
    const res = await api.get("/api/auth/jwt/exchange", { withCredentials: true });
    return res.data; // { issued, tokenType, cookieName, expiresIn, issuedAt }
}

/**
 * 로그아웃 (쿠키 삭제)
 * - 서버 AuthController의 /api/auth/logout 호출
 * - ACCESS_TOKEN 쿠키가 maxAge=0으로 재설정되어 제거됨
 */
export async function logoutJwt() {
    await api.post("/api/auth/logout", null, { withCredentials: true });
}
// src/api/auth.js
import api from "./axiosInstance";

/**
 * JWT 교환 (쿠키 발급)
 * - 세션이 유효할 때 서버가 Set-Cookie: ACCESS_TOKEN=...; HttpOnly 를 내려줍니다.
 * - 브라우저가 자동 저장하므로 JS에서 토큰을 만질 필요가 없습니다.
 * - 응답 바디는 정보용(issued, expiresIn 등)
 */
export async function exchangeJwt() {
    const res = await api.get("/api/auth/exchange");
    return res.data; // { issued, tokenType: "Cookie", cookieName, expiresIn, issuedAt }
}

/**
 * 로그아웃 (쿠키 삭제)
 * - 서버가 ACCESS_TOKEN 쿠키를 maxAge=0으로 재설정해 제거합니다.
 */
export async function logoutJwt() {
    await api.post("/api/auth/logout");
}

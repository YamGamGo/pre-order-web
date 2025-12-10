// src/api/axiosInstance.js
import axios from "axios";

/**
 * 쿠키 기반 인증
 * - withCredentials: true → 브라우저가 쿠키(ACCESS_TOKEN)를 자동 전송
 * - Authorization 헤더는 사용하지 않음
 */
const api = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,
});

// ✅ 헤더 방식 잔여물 제거: Authorization 인터셉터 없음
// api.interceptors.request.use(...) 같은 부분을 두지 마세요.

export default api;

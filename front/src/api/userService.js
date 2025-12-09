import api from "./axiosInstance";

/**
 * 현재 로그인 사용자 프로필 조회
 * - 서버에서 JWT 쿠키 검증 후 사용자 DB 프로필 반환
 */
export async function getUserProfile() {
    const res = await api.get("/api/user/profile", { withCredentials: true });
    return res.data; // { loggedIn: true/false, nickname, profileImage }
}

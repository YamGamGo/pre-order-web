package com.example.tanpo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * 쿠키(ACCESS_TOKEN)에서 JWT를 읽어 인증하는 필터
 * - 유효하면 SecurityContext에 인증 객체 저장
 * - 유효하지 않으면 401 반환
 * - 쿠키가 없으면 다음 필터로 진행(permitAll 경로는 통과, 보호 경로는 401 처리)
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "ACCESS_TOKEN"; // AuthController와 동일해야 함
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 특정 경로는 아예 JWT 필터를 적용하지 않음
     * - /api/auth/** : 로그인/로그아웃/교환 엔드포인트 ,전역 prefix 설정에 따라 중복될 수 있어 보완
     * - /oauth/**, /login/** : 카카오 OAuth 로그인 경로
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/auth/")   // "/api/auth/" 또는 "/auth/" 모두 커버 auth 가 들어가있어서
                || path.startsWith("/oauth/")
                || path.startsWith("/login/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        // 1) 요청 쿠키에서 ACCESS_TOKEN 찾기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        // 2) 토큰이 있으면 검증
        if (token != null && !token.isBlank()) {
            try {
                DecodedJWT decoded = jwtUtil.verify(token);
                String userId = decoded.getSubject(); //db userId 꺼냄

                // 3) 인증 객체 생성 및 SecurityContext 저장
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId, null, Collections.emptyList()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // 만료/위조 등 검증 실패 → 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"Invalid or expired JWT\"}");
                return;
            }
        }

        // 4) 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}

package com.example.tanpo.config;

import com.example.tanpo.security.JwtAuthenticationFilter;
import com.example.tanpo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * JWT 쿠키 방식 보안 설정
 * - 교환 엔드포인트(/api/auth/exchange)가 HttpOnly 쿠키로 JWT를 내려주고
 * - 이후 요청은 브라우저가 쿠키를 자동 첨부
 * - 필터(JwtAuthenticationFilter)가 쿠키에서 JWT를 읽어 인증 처리
 */
@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final JwtUtil jwtUtil;
    private final String allowedOrigin;

    public JwtSecurityConfig(
            JwtUtil jwtUtil,
            @Value("${app.front.redirect}") String frontRedirect
    ) {
        this.jwtUtil = jwtUtil;
        // application.properties가 http://localhost:3000/ 형태일 수 있어 뒤 슬래시 제거
        if (frontRedirect != null && frontRedirect.endsWith("/")) {
            this.allowedOrigin = frontRedirect.substring(0, frontRedirect.length() - 1);
        } else {
            this.allowedOrigin = frontRedirect;
        }
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        return http
                // CORS 허용 (쿠키 전송 필요)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // JWT 사용 시 CSRF 보통 비활성화
                .csrf(csrf -> csrf.disable())
                // 세션은 STATELESS (서버 세션에 인증 상태 저장하지 않음)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 경로별 인가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/oauth/**",
                                "/login/**",
                                "/api/auth/**",   // 교환/로그아웃 엔드포인트는 토큰 없이 접근
                                "/error",
                                "/actuator/health"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // 인증 실패 시 401 JSON 응답
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"error\":\"Unauthorized\"}");
                }))
                // 쿠키에서 JWT를 읽는 필터를 UsernamePasswordAuthenticationFilter 앞에 배치
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // (옵션) 기본 httpBasic 설정
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * CORS 설정
     * - 쿠키 전송을 위해 allowCredentials(true)
     * - 허용 오리진: application.properties의 app.front.redirect (예: http://localhost:3000)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowCredentials(true); // 쿠키 전송 허용 (Access-Control-Allow-Credentials: true)
        cfg.setAllowedOrigins(List.of(allowedOrigin)); // 예: http://localhost:3000
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        // 요청 헤더 허용 (쿠키는 브라우저가 자동 전송, Authorization은 호환 위해 포함)
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","Accept","Origin","X-Requested-With"));
        // 응답에서 노출할 헤더 (필요 시)
        cfg.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}

package com.example.tanpo.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 카카오 로그인 전용 사용자 엔티티입니다.
 * - DB 테이블(users)과 매핑됩니다.
 * - access_token / refresh_token은 길이가 길 수 있어 @Lob로 저장합니다.
 * - created_at / updated_at은 DB에서 자동으로 처리하므로 insertable/updatable을 제한합니다.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUserEntity {

    /** 내부 PK (AUTO_INCREMENT) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 카카오에서 부여하는 고유 ID (UNIQUE, NOT NULL) */
    @Column(name = "kakao_id", nullable = false, unique = true)
    private Long kakaoId;

    /** 이메일 (동의 항목에 따라 null 가능) */
    @Column(length = 255)
    private String email;

    /** 닉네임 (동의 항목에 따라 null 가능) */
    @Column(length = 100)
    private String nickname;

    /** 프로필 이미지 URL (동의 항목에 따라 null 가능) */
    @Column(name = "profile_image", length = 500)
    private String profileImage;

    /** 액세스 토큰 (길이 가변) */
    @Lob
    @Column(name = "access_token")
    private String accessToken;

    /** 리프레시 토큰 (길이 가변) */
    @Lob
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * 생성 시각
     * - DB가 DEFAULT CURRENT_TIMESTAMP로 자동 채웁니다.
     * - 애플리케이션 레벨에서 값을 넣지 않도록 insertable=false, updatable=false로 둡니다.
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    /**
     * 수정 시각
     * - DB가 ON UPDATE CURRENT_TIMESTAMP로 자동 갱신합니다.
     * - 애플리케이션 레벨에서 수정하지 않도록 insertable=false, updatable=false로 둡니다.
     */
    @Column(name = "updated_at", insertable = false, updatable = false)
    private java.sql.Timestamp updatedAt;
}


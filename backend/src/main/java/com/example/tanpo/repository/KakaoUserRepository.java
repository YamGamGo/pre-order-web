package com.example.tanpo.repository;

import com.example.tanpo.entity.KakaoUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface KakaoUserRepository extends JpaRepository<KakaoUserEntity, Long> {

    /** kakao_id 값으로 사용자 조회 */
    Optional<KakaoUserEntity> findByKakaoId(Long kakaoId);
}


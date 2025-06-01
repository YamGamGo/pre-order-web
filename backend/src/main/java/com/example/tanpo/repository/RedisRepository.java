package com.example.tanpo.repository;

import com.example.tanpo.entity.RedisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisRepository extends JpaRepository<RedisEntity, Long> {
    RedisEntity findByKey(String key);
}

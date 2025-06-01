package com.example.tanpo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.tanpo.entity.RedisEntity;
import com.example.tanpo.repository.RedisRepository;

@Service
public class RedisService {

    @Autowired
    private RedisRepository redisRepository;

    // 캐시된 데이터를 Redis에서 가져오기
    @Cacheable(value = "redisData", key = "#key")
    public RedisEntity getRedisData(String key) {
        return redisRepository.findByKey(key);  // Redis 저장소에서 키로 검색
    }

    // 데이터 업데이트 후 캐시 삭제
    @CacheEvict(value = "redisData", key = "#redisEntity.key")
    public void updateRedisData(RedisEntity redisEntity) {
        redisRepository.save(redisEntity);
    }
}

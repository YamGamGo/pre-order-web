package com.example.tanpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.tanpo.entity.RedisEntity;
import com.example.tanpo.service.RedisService;

/// 로그인·권한 확인과 입력 검증이 없어 누구나 자유롭게 조회·수정할 수 있는 취약한 구조임
/// Redis를 직접 사용해본 경험을 보여주기 위한 간단한 코드임
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("{key}")
    public RedisEntity getRedisData(@PathVariable String key) {
        return redisService.getRedisData(key); // Redis에서 데이터 조회
    }

    @PatchMapping()
    public void updateRedisData(@RequestBody RedisEntity redisEntity) {
        redisService.updateRedisData(redisEntity); // Redis 데이터 업데이트
    }
}

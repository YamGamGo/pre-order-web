package com.example.tanpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.tanpo.entity.RedisEntity;
import com.example.tanpo.service.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/get/{key}")
    public RedisEntity getRedisData(@PathVariable String key) {
        return redisService.getRedisData(key); // Redis에서 데이터 조회
    }

    @PostMapping("/update")
    public void updateRedisData(@RequestBody RedisEntity redisEntity) {
        redisService.updateRedisData(redisEntity); // Redis 데이터 업데이트
    }
}

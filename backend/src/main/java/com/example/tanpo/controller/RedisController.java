package com.example.tanpo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.tanpo.entity.RedisEntity;
import com.example.tanpo.service.RedisService;

// TODO: Redis 테스트용 컨트롤러.
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/get/{key}")
    public RedisEntity getRedisData(@PathVariable String key) {
        return redisService.getRedisData(key);
    }

    @PostMapping("/update")
    public void updateRedisData(@RequestBody RedisEntity redisEntity) {
        redisService.updateRedisData(redisEntity);
    }
}


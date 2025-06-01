package com.example.tanpo.config;

import com.example.tanpo.entity.ProductEntity;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductEntity> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ProductEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory); // Redis 연결 설정
        template.setKeySerializer(new StringRedisSerializer()); // 키 직렬화 설정
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 값 직렬화 설정
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        return RedisCacheManager.builder(redisConnectionFactory)// Redis 연결 설정
                .cacheDefaults(cacheConfig)
                .build();
    }
}




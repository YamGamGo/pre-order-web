package com.example.tanpo.service;

import com.example.tanpo.entity.ProductEntity;
import com.example.tanpo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, ProductEntity> redisTemplate;

    private static final String PRODUCT_KEY = "product:";  // Redis 키 접두어

    public Optional<ProductEntity> getExampleByIdOne() {
        // Redis에서 데이터 조회
        System.out.println("Redis에서 ID 1인 데이터를 가져오려고 시도합니다.");
        ProductEntity product = redisTemplate.opsForValue().get(PRODUCT_KEY + "1");

        if (product == null) {
            // Redis에 데이터가 없으면 DB에서 조회
            System.out.println("Redis에 데이터가 없어서 DB에서 데이터를 조회합니다.");
            product = productRepository.findById(1L).orElse(null);

            if (product != null) {
                // DB에서 조회한 데이터를 Redis에 저장
                System.out.println("DB에서 데이터를 조회하여 Redis에 저장합니다.");
                redisTemplate.opsForValue().set(PRODUCT_KEY + "1", product);
            }
        } else {
            System.out.println("Redis에서 데이터를 정상적으로 가져왔습니다.");
        }

        return Optional.ofNullable(product);
    }

    public ProductEntity saveExample(ProductEntity productEntity) {
        // DB에 저장
        ProductEntity savedProduct = productRepository.save(productEntity);

        // Redis에 저장
        System.out.println("DB에 저장한 데이터를 Redis에도 저장합니다.");
        redisTemplate.opsForValue().set(PRODUCT_KEY + savedProduct.getId(), savedProduct);

        return savedProduct;
    }
}

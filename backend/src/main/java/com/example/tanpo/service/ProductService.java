package com.example.tanpo.service;

import com.example.tanpo.entity.ProductEntity;
import com.example.tanpo.repository.ProductRepository;
import lombok.RequiredArgsConstructor; // 추가
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // ID가 1인 데이터만 가져오는 메서드로 변경
    public Optional<ProductEntity> getExampleByIdOne() {
        return productRepository.findById(1L); // ID가 1인 Example 가져오기
    }

    public ProductEntity saveExample(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
}
package com.example.tanpo.repository;

import com.example.tanpo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfomationRepository extends JpaRepository<ProductEntity, Long> {
}


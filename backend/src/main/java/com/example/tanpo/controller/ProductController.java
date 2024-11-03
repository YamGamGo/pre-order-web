package com.example.tanpo.controller;

import com.example.tanpo.entity.ProductEntity;
import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.service.ProductService;
import com.example.tanpo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;  // 오타 수정
    private final ReservationService reservationService;

    @GetMapping("/information")
    public ResponseEntity<ProductEntity> getExampleByIdOne() {
        Optional<ProductEntity> example = productService.getExampleByIdOne();

        if (example.isPresent()) {
            return ResponseEntity.ok(example.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reservation")
    public ResponseEntity<Void> createPurchase(@RequestBody ReservationEntity reservationEntity) {
        reservationService.savePurchase(reservationEntity);
        return ResponseEntity.ok().build();
    }
}







package com.example.tanpo.controller;

import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.service.KakaoPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/kakao")
public class PaymentController {

    @Autowired
    private KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    public String ready(@RequestBody ReservationEntity reservationEntity) {
        return kakaoPayService.ready(reservationEntity);
    }

    @GetMapping("/approve")
    public String approve(@RequestParam("id") Long id, @RequestParam("pg_token") String pgToken) {
        return kakaoPayService.approve(pgToken, id);
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam String id, @RequestParam String pg_token) {
        // 예약 결제 정보를 조회
        Optional<ReservationEntity> purchaseOpt = kakaoPayService.getPurchaseById(Long.parseLong(id));

        if (purchaseOpt.isPresent()) {
            ReservationEntity reservationEntity = purchaseOpt.get();

            // 결제 상태 확인
            if ("PAID".equals(reservationEntity.getStatus())) {
                return ResponseEntity.ok("결제성공");
            } else {
                return ResponseEntity.status(400).body("결제가 완료되지않음");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}









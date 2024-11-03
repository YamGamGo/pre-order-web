package com.example.tanpo.controller;

import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.service.KakaoPayService;
import com.example.tanpo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private KakaoPayService kakaoPayService;

    @Autowired
    private ReservationService reservationService; // ReservationService 추가

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
        Optional<ReservationEntity> purchaseOpt = reservationService.getReservationById(Long.parseLong(id)); // 수정된 부분

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

    @GetMapping("/reservation/{id}") // 예약 정보를 조회하는 API 추가
    public ResponseEntity<ReservationEntity> getReservationById(@PathVariable Long id) {
        Optional<ReservationEntity> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}










package com.example.tanpo.controller;

import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.service.KakaoPayService;
import com.example.tanpo.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final ReservationService reservationService;

    public PaymentController(KakaoPayService kakaoPayService, ReservationService reservationService) {
        this.kakaoPayService = kakaoPayService;
        this.reservationService = reservationService;
    }

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
        Optional<ReservationEntity> purchaseOpt = reservationService.getReservationById(Long.parseLong(id));

        if (purchaseOpt.isPresent()) {
            ReservationEntity reservationEntity = purchaseOpt.get();

            // TODO: PAID 문자열 상수값 따로 정의 해서 쓰기
            if ("PAID".equals(reservationEntity.getStatus())) {
                return ResponseEntity.ok("결제성공");
            } else {
                return ResponseEntity.status(400).body("결제가 완료되지않음");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationEntity> getReservationById(@PathVariable Long id) {
        Optional<ReservationEntity> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}










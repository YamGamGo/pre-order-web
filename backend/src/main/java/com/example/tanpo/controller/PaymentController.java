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
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private KakaoPayService kakaoPayService;

    @Autowired
    private ReservationService reservationService; // ReservationService 추가

    @PostMapping("/ready")
    public String ready(@RequestBody ReservationEntity reservationEntity) {
        return kakaoPayService.ready(reservationEntity); // 결제 준비 요청
    }

    @GetMapping("/approve")
    public String approve(@RequestParam("id") Long id, @RequestParam("pg_token") String pgToken) {
        return kakaoPayService.approve(pgToken, id); // 결제 승인 요청
    }

    @GetMapping()
    public ResponseEntity<String> success(@RequestParam String id, @RequestParam String pg_token) {
        // 예약 결제 정보를 조회
        Optional<ReservationEntity> purchaseOpt = reservationService.getReservationById(Long.parseLong(id)); // 결제 완료여부 검증

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

    @GetMapping("/reservation/{id}") // 예약 DB에서 해당 id 조회
    public ResponseEntity<ReservationEntity> getReservationById(@PathVariable Long id) {
        Optional<ReservationEntity> reservation = reservationService.getReservationById(id); // 특정 예약 정보 조회
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}










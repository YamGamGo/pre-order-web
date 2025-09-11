package com.example.tanpo.service;

import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void savePurchase(ReservationEntity reservationEntity) {
        reservationRepository.save(reservationEntity);
    }

    // 예약 ID로 예약 정보 조회 메소드 추가
    public Optional<ReservationEntity> getReservationById(Long id) {
        return reservationRepository.findById(id); // ID로 예약 조회
    }
}





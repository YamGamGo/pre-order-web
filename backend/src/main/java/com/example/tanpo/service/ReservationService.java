package com.example.tanpo.service;

import com.example.tanpo.entity.ReservationEntity;
import com.example.tanpo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void savePurchase(ReservationEntity reservationEntity) {
        reservationRepository.save(reservationEntity);
    }
}


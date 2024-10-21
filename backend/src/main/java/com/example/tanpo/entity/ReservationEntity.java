package com.example.tanpo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String address;
    private String number;
    private String name;
    private String tid;
    private String status;

}



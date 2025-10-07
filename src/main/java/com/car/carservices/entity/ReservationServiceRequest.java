package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class ReservationServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Long userId;
    private Long carId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    private LocalDate reservationDate;
    // import java.time.LocalTime;
    @Column(name = "reservation_time")
    private LocalTime reservationTime;

    private String reservationStatus;
}

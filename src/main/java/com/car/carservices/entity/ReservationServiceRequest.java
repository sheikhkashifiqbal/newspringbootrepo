package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@Entity
@Table(name = "reservation_service_request")
public class ReservationServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "car_id")
    private Long carId;

    // REPLACED: car_id -> brand_id + model_id
    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    @Column(name = "model_id", nullable = false)
    private Long modelId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus;

    // If you map branch_id too:
     @Column(name = "branch_id")
     private Long branchId;
}

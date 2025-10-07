package com.car.carservices.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationServiceRequestDTO {
    private Long reservationId;
    private Long userId;
    private Long carId;
    private Long serviceId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private String reservationStatus;
}
package com.car.carservices.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationServiceRequestDTO {
    private Long reservationId;
    private Long userId;

    // REPLACED: carId -> brandId + modelId
    private Long brandId;   // FK -> brand.brand_id
    private Long modelId;   // FK -> car_brand_model.id

    private Long serviceId; // keep since it’s in your table
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private String reservationStatus;
    private Long carId;

    // (branchId exists in table; include only if you expose it)
    // private Long branchId;
}

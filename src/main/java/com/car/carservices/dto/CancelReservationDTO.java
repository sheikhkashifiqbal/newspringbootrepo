package com.car.carservices.dto;

import lombok.Data;

@Data
public class CancelReservationDTO {
    private Long id;
    private Long reservationId;
    private String reason;
    private String reasonDetail;
}

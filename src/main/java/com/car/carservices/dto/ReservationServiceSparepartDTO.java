package com.car.carservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationServiceSparepartDTO {

    private Long reservationServiceSparepartId;

    @NotBlank(message = "part_name is required")
    private String partName;

    @NotNull @Min(value = 1, message = "qty must be >= 1")
    private Integer qty;

    @NotNull(message = "reservation_id is required")
    private Long reservationId;

    @NotNull(message = "spareparts_id is required")
    private Long sparepartsId;
}

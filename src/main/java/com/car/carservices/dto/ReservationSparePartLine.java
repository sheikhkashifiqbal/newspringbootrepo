// src/main/java/com/car/carservices/dto/ReservationSparePartLine.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationSparePartLine(
    @JsonProperty("reservation_service_sparepart_id") Long reservationServiceSparepartId,
    @JsonProperty("part_name") String partName,
    @JsonProperty("qty") Integer qty,
    @JsonProperty("spareparts_id") Long sparepartsId,
    @JsonProperty("spareparts_type") String sparepartsType,   // ⬅️ NEW
    @JsonProperty("reservation_id") Long reservationId
) {}

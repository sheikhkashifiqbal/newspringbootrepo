// src/main/java/com/car/carservices/dto/ReservationSummaryResponse.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ReservationSummaryResponse(
    @JsonProperty("reservation_date") String reservationDate,
    @JsonProperty("reservation_time") String reservationTime,
    @JsonProperty("branch_name") String branchName,
    @JsonProperty("address") String address,
    @JsonProperty("city") String city,
    @JsonProperty("logo_img") String logoImg,                    // ⬅️ NEW
    @JsonProperty("brand_name") String brandName,
    @JsonProperty("model_name") String modelName,
    @JsonProperty("service_name") String serviceName,
    @JsonProperty("reservation_status") String reservationStatus,
    @JsonProperty("reservation_id") Long reservationId,
    @JsonProperty("branch_brand_serviceid") Long branchBrandServiceId,
    @JsonProperty("plate_number") String plateNumber,
    @JsonProperty("stars") Integer stars,
    @JsonProperty("reservation_service_sparepart") List<ReservationSparePartLine> spareParts
) {}

// src/main/java/com/car/carservices/dto/SparePartOfferResponse.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SparePartOfferResponse(
    @JsonProperty("sparepartsrequest_id") Long sparePartsRequestId,
    @JsonProperty("date")                 String date,
    @JsonProperty("branch_name")          String branchName,
    @JsonProperty("address")              String address,
    @JsonProperty("city")                 String city,
    @JsonProperty("viN")                  String vin,
    @JsonProperty("spareparts_type")      String sparepartsType,
    @JsonProperty("state")                String state,
    @JsonProperty("spare_part")           List<SparePartLine> sparePart,  // array of details
    @JsonProperty("manager_mobile")       String managerMobile,
    @JsonProperty("id")                   Long id,                         // branch_brand_spare_part.id
    @JsonProperty("request_status")       String requestStatus
) {}

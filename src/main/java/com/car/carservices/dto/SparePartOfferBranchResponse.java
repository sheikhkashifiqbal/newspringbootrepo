// src/main/java/com/car/carservices/dto/SparePartOfferBranchResponse.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SparePartOfferBranchResponse(
    @JsonProperty("sparepartsrequest_id") Long sparePartsRequestId,
    @JsonProperty("brand_id")             Long brandId,      // NEW
    @JsonProperty("brand_name")           String brandName,  // NEW
    @JsonProperty("date")                 String date,
    @JsonProperty("branch_name")          String branchName,
    @JsonProperty("address")              String address,
    @JsonProperty("city")                 String city,
    @JsonProperty("viN")                  String vin,
    @JsonProperty("spareparts_type")      String sparepartsType,
    @JsonProperty("state")                String state,
    @JsonProperty("spare_part")           List<SparePartLine> sparePart,
    @JsonProperty("manager_mobile")       String managerMobile,
    @JsonProperty("id")                   Long id,
    @JsonProperty("request_status")       String requestStatus
) {}

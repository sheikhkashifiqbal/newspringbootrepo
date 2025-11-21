// src/main/java/com/car/carservices/dto/BranchIdRequest.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchIdRequest(
    @JsonProperty("branch_id") Long branchId
) {}

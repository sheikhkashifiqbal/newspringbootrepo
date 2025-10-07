// src/main/java/com/yourapp/branchservices/dto/BranchServicesRequest.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchServicesRequest {
    @JsonProperty("branch_id")
    private Long branchId;

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
}

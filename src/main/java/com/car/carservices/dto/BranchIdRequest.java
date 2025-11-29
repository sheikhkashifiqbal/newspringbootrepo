// src/main/java/com/car/carservices/dto/BranchIdRequest.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchIdRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    public Long getBranchId() {
        return branchId;
    }
    public Long branchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}

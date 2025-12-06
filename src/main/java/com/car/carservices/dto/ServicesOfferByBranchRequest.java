package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicesOfferByBranchRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    public ServicesOfferByBranchRequest() {
    }

    public ServicesOfferByBranchRequest(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}

package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchBrandSparePartsRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("brand_id")
    private Long brandId;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}

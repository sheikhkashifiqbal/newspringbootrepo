package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BABranchBrandSparePartStatusRequestDTO {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("status")
    private String status; // "active" | "inactive"

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
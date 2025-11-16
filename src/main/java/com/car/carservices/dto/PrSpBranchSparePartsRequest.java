package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrSpBranchSparePartsRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("service_id")
    private Long serviceId; // optional, reserved for future use

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
}

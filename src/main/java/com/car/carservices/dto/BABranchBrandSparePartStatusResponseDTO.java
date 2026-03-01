package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BABranchBrandSparePartStatusResponseDTO {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("updated_count")
    private Integer updatedCount;

    public BABranchBrandSparePartStatusResponseDTO() {}

    public BABranchBrandSparePartStatusResponseDTO(Long branchId, Long brandId, String status, Integer updatedCount) {
        this.branchId = branchId;
        this.brandId = brandId;
        this.status = status;
        this.updatedCount = updatedCount;
    }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getUpdatedCount() { return updatedCount; }
    public void setUpdatedCount(Integer updatedCount) { this.updatedCount = updatedCount; }
}
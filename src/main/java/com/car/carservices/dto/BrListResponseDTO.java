package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrListResponseDTO {

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

    private String status;

    public BrListResponseDTO() {}

    public BrListResponseDTO(Long brandId, String brandName, String status) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.status = status;
    }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

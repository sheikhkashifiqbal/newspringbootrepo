package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRBrandDTO {
    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

    public PRBrandDTO(Long brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public Long getBrandId() { return brandId; }
    public String getBrandName() { return brandName; }
}

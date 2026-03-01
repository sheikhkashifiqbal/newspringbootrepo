package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SPBrandSparePartsResponseDTO {

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

    // status from branch_brand_spare_part table
    @JsonProperty("status")
    private String status;

    // NOTE: you wrote "available_spaeparts" (typo) - I keep EXACT same key
    @JsonProperty("available_spaeparts")
    private List<SPAvailableSparePartDTO> availableSpaeparts = new ArrayList<>();

    public SPBrandSparePartsResponseDTO() {}

    public SPBrandSparePartsResponseDTO(Long brandId, String brandName, String status) {
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

    public List<SPAvailableSparePartDTO> getAvailableSpaeparts() { return availableSpaeparts; }
    public void setAvailableSpaeparts(List<SPAvailableSparePartDTO> availableSpaeparts) { this.availableSpaeparts = availableSpaeparts; }
}
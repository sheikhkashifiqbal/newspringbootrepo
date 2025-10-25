package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BranchServicesResponse {

    @JsonProperty("brand_id")          // <-- NEW
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("available_services")
    private List<PRBranchServiceItem> availableServices;

    // UPDATED constructor (added brandId as first param)
    public BranchServicesResponse(Long brandId, String brandName, String status, List<PRBranchServiceItem> availableServices) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.status = status;
        this.availableServices = availableServices;
    }

    public Long getBrandId() { return brandId; }       // <-- NEW getter
    public String getBrandName() { return brandName; }
    public String getStatus() { return status; }
    public List<PRBranchServiceItem> getAvailableServices() { return availableServices; }
}

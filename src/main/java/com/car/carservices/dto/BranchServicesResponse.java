// src/main/java/com/car/carservices/dto/BranchServicesResponse.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BranchServicesResponse {

    @JsonProperty("brand_id")
    private Long brandId;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("brand_icon")   // ✅ NEW
    private String brandIcon;

    @JsonProperty("status")
    private String status;

    @JsonProperty("available_services")
    private List<PRBranchServiceItem> availableServices;

    public BranchServicesResponse(Long brandId, String brandName, String brandIcon, String status,
                                  List<PRBranchServiceItem> availableServices) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandIcon = brandIcon;
        this.status = status;
        this.availableServices = availableServices;
    }

    public Long getBrandId() { return brandId; }
    public String getBrandName() { return brandName; }
    public String getBrandIcon() { return brandIcon; }  // ✅ NEW getter
    public String getStatus() { return status; }
    public List<PRBranchServiceItem> getAvailableServices() { return availableServices; }
}

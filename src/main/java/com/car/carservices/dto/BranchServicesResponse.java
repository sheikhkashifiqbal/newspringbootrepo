// src/main/java/com/yourapp/branchservices/dto/BranchServicesResponse.java
package com.car.carservices.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BranchServicesResponse {
    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("available_services")
    private List<String> availableServices;

    public BranchServicesResponse(String brandName, String status, List<String> availableServices) {
        this.brandName = brandName;
        this.status = status;
        this.availableServices = availableServices;
    }

    public String getBrandName() { return brandName; }
    public String getStatus() { return status; }
    public List<String> getAvailableServices() { return availableServices; }
}
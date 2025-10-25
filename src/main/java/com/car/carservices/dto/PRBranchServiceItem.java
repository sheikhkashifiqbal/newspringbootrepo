package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRBranchServiceItem {

    @JsonProperty("id")           // branch_brand_service.id
    private Long id;

    @JsonProperty("service_name") // service_entity.service_name
    private String serviceName;

    @JsonProperty("status")       // branch_brand_service.status (NEW)
    private String status;

    public PRBranchServiceItem(Long id, String serviceName, String status) {
        this.id = id;
        this.serviceName = serviceName;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
    public String getStatus() { return status; }
}

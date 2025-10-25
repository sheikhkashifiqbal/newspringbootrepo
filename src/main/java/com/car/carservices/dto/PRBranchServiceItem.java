package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRBranchServiceItem {
    @JsonProperty("id")
    private Long id;                 // branch_brand_service.id

    @JsonProperty("service_name")
    private String serviceName;      // service_entity.service_name

    public PRBranchServiceItem(Long id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
}

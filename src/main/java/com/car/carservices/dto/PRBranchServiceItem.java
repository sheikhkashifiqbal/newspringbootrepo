// src/main/java/com/car/carservices/dto/PRBranchServiceItem.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRBranchServiceItem {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("status")
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

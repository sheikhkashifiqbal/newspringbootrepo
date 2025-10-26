package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PRServiceDTO {
    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("service_name")
    private String serviceName;

    public PRServiceDTO(Long serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Long getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
}

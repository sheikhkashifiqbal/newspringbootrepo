package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicesOfferByBranchResponse {

    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("service_name")
    private String serviceName;

    public ServicesOfferByBranchResponse() {
    }

    public ServicesOfferByBranchResponse(Long serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

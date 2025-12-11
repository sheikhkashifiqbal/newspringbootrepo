package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisableServiceRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("service_id")
    private Long serviceId;

    private String status; // active | inactive

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

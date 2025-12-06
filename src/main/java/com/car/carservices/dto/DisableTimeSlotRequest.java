package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisableTimeSlotRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("service_id")
    private Long serviceId; // optional

    @JsonProperty("time_slot")
    private String timeSlot;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}

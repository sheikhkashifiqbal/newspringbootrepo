package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisableTimeSlotRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    // optional: can be 1, "1", "" or null
    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("time_slot")
    private String timeSlot;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}

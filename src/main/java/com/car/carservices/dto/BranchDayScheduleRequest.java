package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchDayScheduleRequest {

    @JsonProperty("branch_id")
    private Long branchId;

    @JsonProperty("reservation_date")
    private String reservationDate; // "YYYY-MM-DD"

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
}

package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BranchDayScheduleResponse {

    @JsonProperty("working_day")
    private String workingDay;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("reservation_list")
    private List<BranchDayReservationItem> reservationList;

    public BranchDayScheduleResponse() {}

    public BranchDayScheduleResponse(String workingDay, String from, String to,
                                     List<BranchDayReservationItem> reservationList) {
        this.workingDay = workingDay;
        this.from = from;
        this.to = to;
        this.reservationList = reservationList;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<BranchDayReservationItem> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<BranchDayReservationItem> reservationList) {
        this.reservationList = reservationList;
    }
}

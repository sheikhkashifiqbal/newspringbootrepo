package com.car.carservices.dto;

public class PaymentServicesGenerateRequestDTO {

    // month like "02"
    private String month;

    // year like 2025
    private Integer year;

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
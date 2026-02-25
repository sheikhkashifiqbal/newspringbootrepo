package com.car.carservices.dto;

public class SparePartsPaymentReportRequestDTO {
    private Integer month; // 1..12
    private Integer year;

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
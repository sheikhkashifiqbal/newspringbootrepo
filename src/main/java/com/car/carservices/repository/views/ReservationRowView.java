// src/main/java/com/car/carservices/repository/views/ReservationRowView.java
package com.car.carservices.repository.views;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRowView {
    Long getReservationId();
    LocalDate getReservationDate();
    LocalTime getReservationTime();
    String getBranchName();
    String getAddress();
    String getCity();
    String getBrandName();
    String getModelName();
    String getServiceName();
    String getReservationStatus();
    Long getBranchBrandServiceId();
    String getPlateNumber();
    Double getStars();
}

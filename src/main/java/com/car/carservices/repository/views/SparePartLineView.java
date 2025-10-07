// src/main/java/com/car/carservices/repository/views/SparePartLineView.java
package com.car.carservices.repository.views;

public interface SparePartLineView {
    Long getReservationServiceSparepartId();
    String getPartName();
    Integer getQty();
    Long getReservationId();
}

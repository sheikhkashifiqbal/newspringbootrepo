package com.car.carservices.service;

import com.car.carservices.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {
    List<ReservationDTO> getReservationsByStatus(String status);
}

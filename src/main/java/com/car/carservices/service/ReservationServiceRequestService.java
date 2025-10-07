package com.car.carservices.service;

import com.car.carservices.dto.ReservationServiceRequestDTO;
import java.util.List;

public interface ReservationServiceRequestService {
    ReservationServiceRequestDTO create(ReservationServiceRequestDTO dto);
    ReservationServiceRequestDTO get(Long id);
    List<ReservationServiceRequestDTO> getAll();
    ReservationServiceRequestDTO update(Long id, ReservationServiceRequestDTO dto);
    void delete(Long id);
}
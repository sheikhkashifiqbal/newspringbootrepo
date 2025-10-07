package com.car.carservices.service;

import com.car.carservices.dto.CancelReservationDTO;
import java.util.List;

public interface CancelReservationService {
    CancelReservationDTO create(CancelReservationDTO dto);
    CancelReservationDTO get(Long id);
    List<CancelReservationDTO> getAll();
    CancelReservationDTO update(Long id, CancelReservationDTO dto);
    void delete(Long id);
}
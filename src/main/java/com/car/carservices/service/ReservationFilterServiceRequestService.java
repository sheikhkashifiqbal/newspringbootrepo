package com.car.carservices.service;

import com.car.carservices.dto.ReservationDTO;
import com.car.carservices.entity.ReservationServiceRequest;

import java.util.List;

public interface ReservationFilterServiceRequestService {
    List<ReservationServiceRequest> filterReservations(ReservationDTO filterDTO);
}

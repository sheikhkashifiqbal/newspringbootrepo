package com.car.carservices.service.impl;

import com.car.carservices.dto.ReservationDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import com.car.carservices.repository.ReservationFilterServiceRequestRepository;
import com.car.carservices.service.ReservationFilterServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationFilterServiceRequestServiceImpl implements ReservationFilterServiceRequestService {

    @Autowired
    private ReservationFilterServiceRequestRepository reservationRepo;

    @Override
    public List<ReservationServiceRequest> filterReservations(ReservationDTO filterDTO) {
        return reservationRepo.filterReservations(
                filterDTO.getServiceIds(),
                filterDTO.getReservationTimes(),
                filterDTO.getReservationDate()
        );
    }
}

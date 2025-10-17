package com.car.carservices.service.impl;

import com.car.carservices.dto.ReservationDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import com.car.carservices.repository.ReservationRepository;
import com.car.carservices.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private ReservationDTO convertToDTO(ReservationServiceRequest entity) {
        ReservationDTO dto = new ReservationDTO();
        dto.setReservationId(entity.getReservationId());
        dto.setUserId(entity.getUserId());
        //dto.setCarId(entity.getCarId());
       // dto.setServiceId(entity.getServiceId());
        dto.setReservationDate(entity.getReservationDate());
        dto.setReservationTime(entity.getReservationTime());
        dto.setReservationStatus(entity.getReservationStatus());
        return dto;
    }

    @Override
    public List<ReservationDTO> getReservationsByStatus(String status) {
        return reservationRepository.findByReservationStatus(status)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
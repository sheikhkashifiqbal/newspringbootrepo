package com.car.carservices.mapper;

import com.car.carservices.dto.ReservationServiceRequestDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class ReservationServiceRequestMapper {
    public ReservationServiceRequestDTO toDTO(ReservationServiceRequest entity) {
        ReservationServiceRequestDTO dto = new ReservationServiceRequestDTO();
        dto.setReservationId(entity.getReservationId());
        dto.setUserId(entity.getUserId());
        dto.setCarId(entity.getCarId());
      //  dto.setServiceId(entity.getServiceId());
        dto.setReservationDate(entity.getReservationDate());
        dto.setReservationTime(entity.getReservationTime());
        dto.setReservationStatus(entity.getReservationStatus());
        return dto;
    }

    public ReservationServiceRequest toEntity(ReservationServiceRequestDTO dto) {
        ReservationServiceRequest entity = new ReservationServiceRequest();
        entity.setReservationId(dto.getReservationId());
        entity.setUserId(dto.getUserId());
        entity.setCarId(dto.getCarId());
      //  entity.setServiceId(dto.getServiceId());
        entity.setReservationDate(dto.getReservationDate());
        entity.setReservationTime(dto.getReservationTime());
        entity.setReservationStatus(dto.getReservationStatus());
        return entity;
    }
}
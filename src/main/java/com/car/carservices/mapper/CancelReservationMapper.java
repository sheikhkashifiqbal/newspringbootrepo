package com.car.carservices.mapper;

import com.car.carservices.dto.CancelReservationDTO;
import com.car.carservices.entity.CancelReservation;
import org.springframework.stereotype.Component;

@Component
public class CancelReservationMapper {
    public CancelReservationDTO toDTO(CancelReservation entity) {
        CancelReservationDTO dto = new CancelReservationDTO();
        dto.setId(entity.getId());
        dto.setReservationId(entity.getReservationId());
        dto.setReason(entity.getReason());
        dto.setReasonDetail(entity.getReasonDetail());
        return dto;
    }

    public CancelReservation toEntity(CancelReservationDTO dto) {
        CancelReservation entity = new CancelReservation();
        entity.setId(dto.getId());
        entity.setReservationId(dto.getReservationId());
        entity.setReason(dto.getReason());
        entity.setReasonDetail(dto.getReasonDetail());
        return entity;
    }
}
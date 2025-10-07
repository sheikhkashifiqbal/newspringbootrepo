package com.car.carservices.mapper;

import com.car.carservices.dto.ReservationServiceSparepartDTO;
import com.car.carservices.entity.ReservationServiceSparepart;

public class ReservationServiceSparepartMapper {

    public static ReservationServiceSparepart toEntity(ReservationServiceSparepartDTO dto) {
        ReservationServiceSparepart e = new ReservationServiceSparepart();
        e.setReservationServiceSparepartId(dto.getReservationServiceSparepartId());
        e.setPartName(dto.getPartName());
        e.setQty(dto.getQty());
        e.setReservationId(dto.getReservationId());
        e.setSparepartsId(dto.getSparepartsId());
        return e;
    }

    public static ReservationServiceSparepartDTO toDTO(ReservationServiceSparepart e) {
        ReservationServiceSparepartDTO dto = new ReservationServiceSparepartDTO();
        dto.setReservationServiceSparepartId(e.getReservationServiceSparepartId());
        dto.setPartName(e.getPartName());
        dto.setQty(e.getQty());
        dto.setReservationId(e.getReservationId());
        dto.setSparepartsId(e.getSparepartsId());
        return dto;
    }

    public static void merge(ReservationServiceSparepart e, ReservationServiceSparepartDTO dto) {
        if (dto.getPartName() != null) e.setPartName(dto.getPartName());
        if (dto.getQty() != null) e.setQty(dto.getQty());
        if (dto.getReservationId() != null) e.setReservationId(dto.getReservationId());
        if (dto.getSparepartsId() != null) e.setSparepartsId(dto.getSparepartsId());
    }
}

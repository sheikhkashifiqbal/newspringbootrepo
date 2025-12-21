package com.car.carservices.mapper;

import com.car.carservices.dto.ReservationServiceRequestDTO;
import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class ReservationServiceRequestMapper {

    public ReservationServiceRequestDTO toDTO(ReservationServiceRequest e) {
        if (e == null) return null;
        ReservationServiceRequestDTO d = new ReservationServiceRequestDTO();
        d.setReservationId(e.getReservationId());
        d.setUserId(e.getUserId());
        d.setCarId(e.getCarId());
        d.setBrandId(e.getBrandId());
        d.setModelId(e.getModelId());
        d.setServiceId(e.getServiceId());
        d.setReservationDate(e.getReservationDate());
        d.setReservationTime(e.getReservationTime());
        d.setReservationStatus(e.getReservationStatus());
        d.setBranchId(e.getBranchId());
        return d;
    }

    public ReservationServiceRequest toEntity(ReservationServiceRequestDTO d) {
        if (d == null) return null;
        ReservationServiceRequest e = new ReservationServiceRequest();
        e.setReservationId(d.getReservationId());
        e.setUserId(d.getUserId());
        e.setCarId(d.getCarId());
        e.setBrandId(d.getBrandId());
        e.setModelId(d.getModelId());
        e.setServiceId(d.getServiceId());
        e.setReservationDate(d.getReservationDate());
        e.setReservationTime(d.getReservationTime());
        e.setReservationStatus(d.getReservationStatus());
         e.setBranchId(d.getBranchId());
        return e;
    }
}

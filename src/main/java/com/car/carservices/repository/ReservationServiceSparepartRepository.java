package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceSparepart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationServiceSparepartRepository
        extends JpaRepository<ReservationServiceSparepart, Long> {

    List<ReservationServiceSparepart> findByReservationId(Long reservationId);
}

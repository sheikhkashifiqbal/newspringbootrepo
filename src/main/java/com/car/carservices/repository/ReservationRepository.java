package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationServiceRequest, Long> {
    List<ReservationServiceRequest> findByReservationStatus(String reservationStatus);
}

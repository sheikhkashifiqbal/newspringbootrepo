package com.car.carservices.repository;

import com.car.carservices.entity.CancelReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelReservationRepository extends JpaRepository<CancelReservation, Long> {
}
package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationServiceRequestRepository extends JpaRepository<ReservationServiceRequest, Long> {
}
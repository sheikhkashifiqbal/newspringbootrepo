package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationFilterServiceRequestRepository extends JpaRepository<ReservationServiceRequest, Long> {

    @Query("SELECT r FROM ReservationServiceRequest r " +
           "WHERE r.service.serviceId IN :serviceIds " +
           "AND r.reservationTime IN :reservationTimes " +
           "AND r.reservationDate = :reservationDate")
    List<ReservationServiceRequest> filterReservations(
            List<Long> serviceIds,
            List<LocalTime> reservationTimes,
            LocalDate reservationDate
    );
}

// com/car/carservices/repository/ReservationCounterRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationCounterRepository extends JpaRepository<ReservationServiceRequest, Long> {

    @Query(
        value = """
            SELECT COUNT(*) 
            FROM reservation_service_request r
            WHERE r.branch_id = :branchId
              AND r.service_id = :serviceId
              AND r.reservation_date = :reservationDate
              AND (r.reservation_status IS NULL OR LOWER(r.reservation_status) NOT IN ('cancelled','rejected'))
              AND r.reservation_time = :reservationTime
        """,
        nativeQuery = true
    )
    long countByBranchServiceDateTime(@Param("branchId") Long branchId,
                                      @Param("serviceId") Long serviceId,
                                      @Param("reservationDate") LocalDate reservationDate,
                                      @Param("reservationTime") LocalTime reservationTime);
}

package com.car.carservices.repository;

import com.car.carservices.entity.ReservationServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationCheckRepository extends JpaRepository<ReservationServiceRequest, Long> {

    @Query(value = """
        SELECT COUNT(*)
        FROM reservation_service_request
        WHERE branch_id = :branchId
          AND reservation_time = CAST(:timeSlot AS time)
          AND reservation_date >= CURRENT_DATE
          AND (:serviceId IS NULL OR service_id = :serviceId)
    """, nativeQuery = true)
    int countReservations(
            @Param("branchId") Long branchId,
            @Param("timeSlot") String timeSlot,
            @Param("serviceId") Long serviceId
    );
}

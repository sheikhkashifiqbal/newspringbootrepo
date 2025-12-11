package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandService;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisableServiceRepository extends JpaRepository<BranchBrandService, Long> {

    // Get all entries by branch + service (needed for update)
    List<BranchBrandService> findByBranch_BranchIdAndService_ServiceId(Long branchId, Long serviceId);

    // Check reservation dependency rule
    @Query(value = """
        SELECT COUNT(*) 
        FROM reservation_service_request
        WHERE branch_id = :branchId
          AND service_id = :serviceId
          AND reservation_date >= CURRENT_DATE
    """, nativeQuery = true)
    int countFutureReservations(@Param("branchId") Long branchId,
                                @Param("serviceId") Long serviceId);
}

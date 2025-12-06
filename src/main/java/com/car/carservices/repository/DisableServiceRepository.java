package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface DisableServiceRepository extends JpaRepository<BranchBrandService, Long> {

    // ✔ Check if any CURRENT or FUTURE reservations exist
    @Query(value = """
            SELECT COUNT(*) FROM reservation_service_request
            WHERE branch_id = :branchId
              AND service_id = :serviceId
              AND reservation_date >= CURRENT_DATE
            """, nativeQuery = true)
    int countActiveReservations(@Param("branchId") Long branchId,
                                @Param("serviceId") Long serviceId);

    // ✔ Update status in branch_brand_service
    @Query(value = """
            UPDATE branch_brand_service
            SET status = :status
            WHERE branch_id = :branchId AND service_id = :serviceId
            """, nativeQuery = true)
    void updateServiceStatus(@Param("branchId") Long branchId,
                             @Param("serviceId") Long serviceId,
                             @Param("status") String status);
}

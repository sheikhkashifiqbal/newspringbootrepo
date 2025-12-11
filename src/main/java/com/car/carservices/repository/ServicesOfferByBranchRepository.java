package com.car.carservices.repository;

import com.car.carservices.dto.ServicesOfferByBranchProjection;
import com.car.carservices.entity.BranchBrandService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesOfferByBranchRepository extends JpaRepository<BranchBrandService, Long> {

    /**
     * Native SQL to avoid JPQL property name issues.
     *
     * Tables:
     *  - branch_brand_service (bbs)
     *  - service_entity (se)
     */
@Query(value = """
        SELECT DISTINCT
            bbs.service_id AS serviceId,
            se.service_name AS serviceName
        FROM branch_brand_service bbs
        JOIN service_entity se ON bbs.service_id = se.service_id
        WHERE bbs.branch_id = :branchId
        
        ORDER BY se.service_name ASC
        """,
        nativeQuery = true)
List<ServicesOfferByBranchProjection> findServicesByBranchId(@Param("branchId") Long branchId);

}

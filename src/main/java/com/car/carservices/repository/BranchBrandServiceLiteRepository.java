// com/car/carservices/repository/BranchBrandServiceLiteRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandService;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BranchBrandServiceLiteRepository extends JpaRepository<BranchBrandService, Long> {

    @Query("""
        SELECT bbs.qty FROM BranchBrandService bbs
        WHERE bbs.branch.branchId = :branchId
          AND bbs.brand.brandId   = :brandId
          AND bbs.service.serviceId = :serviceId
    """)
    Optional<Integer> qty(@Param("branchId") Long branchId,
                          @Param("brandId") Long brandId,
                          @Param("serviceId") Long serviceId);
}

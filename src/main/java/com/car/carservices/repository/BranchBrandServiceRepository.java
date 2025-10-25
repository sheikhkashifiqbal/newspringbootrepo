package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandService;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BranchBrandServiceRepository extends JpaRepository<BranchBrandService, Long> {

    List<BranchBrandService> findByBranch_BranchId(Long branchId);

    // NEW: bulk status update by (branch, brand)
    @Modifying
    @Transactional
    @Query("""
        update BranchBrandService bbs
           set bbs.status = :status
         where bbs.branch.branchId = :branchId
           and bbs.brand.brandId   = :brandId
    """)
    int updateStatusByBranchAndBrand(@Param("branchId") Long branchId,
                                     @Param("brandId")  Long brandId,
                                     @Param("status")   String status);
}

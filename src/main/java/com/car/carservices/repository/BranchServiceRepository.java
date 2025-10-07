package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchServiceRepository extends JpaRepository<Branch, Long> {

    @Query("""
        SELECT DISTINCT b FROM Branch b
        JOIN BranchBrandService bbs ON b.branchId = bbs.branch.branchId
        JOIN CarBrandModel cbm ON cbm.brand.brandId = bbs.brand.brandId
        JOIN Brand br ON br.brandId = cbm.brand.brandId
        JOIN ServiceEntity se ON se.serviceId = bbs.service.serviceId
        WHERE br.brandName = :brand
          AND cbm.modelName = :model
          AND se.serviceName = :service
          AND LOWER(b.location) = LOWER(:location)
    """)
    List<Branch> searchBranches(
        @Param("brand") String brand,
        @Param("model") String model,
        @Param("service") String service,
        @Param("location") String location
    );
}

package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandSparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrListRepository extends JpaRepository<BranchBrandSparePart, Long> {

    interface BrListBrandProjection {
        Long getBrand_id();
        String getBrand_name();
        String getStatus();
    }

    @Query(value = """
        SELECT DISTINCT b.brand_id AS brand_id, b.brand_name AS brand_name, b.status AS status
        FROM branch_brand_spare_part bbsp
        JOIN brand b ON b.brand_id = bbsp.brand_id
        WHERE bbsp.branch_id = :branchId
        ORDER BY b.brand_id
        """, nativeQuery = true)
    List<BrListBrandProjection> findBrandsByBranchId(@Param("branchId") Long branchId);
}

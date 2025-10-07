package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchBrandRepository extends JpaRepository<Branch, Long> {

    @Query("SELECT DISTINCT b FROM Branch b " +
           "JOIN BranchBrandService bbs ON b.branchId = bbs.branch.branchId " +
           "WHERE bbs.brand.brandId = :brandId AND bbs.service.serviceId IN :serviceIds")
    List<Branch> findBranchesByBrandAndServices(Long brandId, List<Long> serviceIds);
}

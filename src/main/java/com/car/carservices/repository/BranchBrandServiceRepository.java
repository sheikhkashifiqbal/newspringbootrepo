package com.car.carservices.repository;

import com.car.carservices.entity.BranchBrandService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchBrandServiceRepository extends JpaRepository<BranchBrandService, Long> {
    List<BranchBrandService> findByBranch_BranchId(Long branchId);
}

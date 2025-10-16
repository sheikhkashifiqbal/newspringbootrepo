package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("select b from Branch b where b.company.companyId = :companyId")
    List<Branch> findByCompanyCompanyId(@Param("companyId") Long companyId);

    @Query("select b from Branch b where b.company.companyId = :companyId")
    List<Branch> findAllByCompanyId(@Param("companyId") Long companyId);

    // NEW: uniqueness checks for loginEmail (case-insensitive)
    boolean existsByLoginEmailIgnoreCase(String loginEmail);
    boolean existsByLoginEmailIgnoreCaseAndBranchIdNot(String loginEmail, Long branchId);
    

    // NEW (for auth)
    Optional<Branch> findFirstByLoginEmailIgnoreCase(String loginEmail);
    Optional<Branch> findFirstByMobileIgnoreCase(String mobile);
    
}


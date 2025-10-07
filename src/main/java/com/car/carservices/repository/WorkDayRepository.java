// com/car/carservices/repository/WorkDayRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    // ----- reads -----
    List<WorkDay> findByBranch_BranchId(Long branchId);

    Optional<WorkDay> findByBranch_BranchIdAndWorkingDay(Long branchId, String workingDay);

    Optional<WorkDay> findByBranch_BranchIdAndWorkingDayIgnoreCase(Long branchId, String workingDay);

    @Query("""
       SELECT COUNT(w) FROM WorkDay w
       WHERE w.branch.branchId = :branchId
         AND LOWER(w.workingDay) = LOWER(:day)
         AND LOWER(w.status) = 'active'
    """)
    long countActiveOnDay(@Param("branchId") Long branchId, @Param("day") String day);

    // ----- deletes / maintenance -----
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM WorkDay w WHERE w.branch.branchId = :branchId")
    void deleteByBranch_BranchId(@Param("branchId") Long branchId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM WorkDay w WHERE w.branch.branchId = :branchId AND w.workingDay NOT IN :days")
    void deleteByBranchAndWorkingDayNotIn(@Param("branchId") Long branchId,
                                          @Param("days") Collection<String> days);
}

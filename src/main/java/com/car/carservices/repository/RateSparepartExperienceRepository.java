package com.car.carservices.repository;

import com.car.carservices.entity.RateSparepartExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RateSparepartExperienceRepository
        extends JpaRepository<RateSparepartExperienceEntity, Long> {

    // Existing method (unchanged)
    List<RateSparepartExperienceEntity> findByBranchBrandSparepartId(Long branchBrandSparepartId);

    /**
     * Returns:
     *  [0] RateSparepartExperienceEntity
     *  [1] user full_name
     *  [2] sparepartsrequest_id
     */
    @Query("""
        select r,
               coalesce(u.fullName, ''),
               spr.sparepartsrequestId
        from RateSparepartExperienceEntity r
        join com.car.carservices.entity.BranchBrandSparePart bb
             on bb.id = r.branchBrandSparepartId
        left join com.car.carservices.entity.UserRegistration u
             on u.id = r.userId
        left join com.car.carservices.entity.SparePartsRequest spr
             on spr.sparepartsrequestId = r.sparepartsrequestId
        where bb.branch.branchId = :branchId
        order by r.rateExperienceId desc
    """)
    List<Object[]> findRatingsRawByBranchId(@Param("branchId") Long branchId);

    @Query("""
        select r.stars, count(r)
        from RateSparepartExperienceEntity r
        join com.car.carservices.entity.BranchBrandSparePart bb
             on bb.id = r.branchBrandSparepartId
        where bb.branch.branchId = :branchId
        group by r.stars
    """)
    List<Object[]> countByStarsForBranch(@Param("branchId") Long branchId);

    @Query("""
        select count(r)
        from RateSparepartExperienceEntity r
        join com.car.carservices.entity.BranchBrandSparePart bb
             on bb.id = r.branchBrandSparepartId
        where bb.branch.branchId = :branchId
    """)
    long totalCountForBranch(@Param("branchId") Long branchId);
}

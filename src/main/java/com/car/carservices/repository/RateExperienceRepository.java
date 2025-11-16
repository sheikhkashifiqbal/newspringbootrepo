package com.car.carservices.repository;

import com.car.carservices.entity.RateExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateExperienceRepository extends JpaRepository<RateExperience, Long> {

    @Query(value = """
        SELECT re.*
        FROM rate_experience re
        JOIN branch_brand_service bbs ON bbs.id = re.branch_brand_serviceid
        WHERE bbs.branch_id = :branchId
        ORDER BY re.date DESC
    """, nativeQuery = true)
    List<RateExperience> findAllByBranchId(@Param("branchId") Long branchId);

    @Query(value = """
        SELECT COUNT(*) FROM rate_experience re
        JOIN branch_brand_service bbs ON bbs.id = re.branch_brand_serviceid
        WHERE bbs.branch_id = :branchId
    """, nativeQuery = true)
    Long countByBranchId(@Param("branchId") Long branchId);

    @Query(value = """
        SELECT COUNT(*) FROM rate_experience re
        JOIN branch_brand_service bbs ON bbs.id = re.branch_brand_serviceid
        WHERE bbs.branch_id = :branchId AND re.stars = :stars
    """, nativeQuery = true)
    Long countByStars(@Param("branchId") Long branchId, @Param("stars") int stars);
        @Query(value = """
        SELECT AVG(re.stars)
        FROM rate_experience re
        JOIN branch_brand_service bbs ON bbs.id = re.branch_brand_serviceid
        WHERE bbs.branch_id = :branchId
    """, nativeQuery = true)
    Double avgStarsForBranch(@Param("branchId") Long branchId);

}

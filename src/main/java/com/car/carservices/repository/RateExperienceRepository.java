// com/car/carservices/repository/RateExperienceRepository.java
package com.car.carservices.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface RateExperienceRepository extends JpaRepository<com.car.carservices.entity.RateExperience, Long> {

    // NOTE: If your column is spelled 'branch_brand_serviceid', rename below accordingly.
    @Query(value = """
        SELECT AVG(re.stars)
        FROM rate_experience re
        JOIN branch_brand_service bbs ON bbs.id = re.branch_brand_serviceid
        WHERE bbs.branch_id = :branchId
    """, nativeQuery = true)
    Double avgStarsForBranch(@Param("branchId") Long branchId);
}

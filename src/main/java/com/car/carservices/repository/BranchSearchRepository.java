// com/car/carservices/repository/BranchSearchRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchSearchRepository extends JpaRepository<Branch, Long> {

    @Query(value = """
        SELECT DISTINCT b.*
        FROM branch b
        JOIN branch_brand_service bbs ON b.branch_id = bbs.branch_id
        JOIN service_entity se        ON se.service_id = bbs.service_id
        JOIN brand br                 ON br.brand_id = bbs.brand_id
        LEFT JOIN car_brand_model cbm ON cbm.brand_id = br.brand_id
        WHERE (:brand   IS NULL OR lower(br.brand_name)    = lower(:brand))
          AND (:model   IS NULL OR lower(cbm.model_name)   = lower(:model))
          AND (:service IS NULL OR lower(se.service_name)  = lower(:service))
          AND b.status = 'approved'
        """, nativeQuery = true)
    List<Branch> searchBranchesFlexibleNoCity(@Param("brand")   String brand,
                                              @Param("model")   String model,
                                              @Param("service") String service);
}

package com.car.carservices.repository;

//import com.car.carservices.dto.BranchSparePartResultDTO;
import com.car.carservices.entity.BranchBrandSparePart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchBrandSparePartsRepository extends JpaRepository<BranchBrandSparePart, Long> {

    @Query(value = """
            SELECT DISTINCT b.branch_id   AS branchId,
                   b.branch_name         AS branchName,
                   b.branch_address      AS branchAddress,
                   br.brand_name         AS brandName,
                   b.location            AS location,
                   COUNT(*)              AS totalMatchedParts
            FROM branch_brand_spare_part bbsp
            JOIN branch b               ON b.branch_id   = bbsp.branch_id
            JOIN brand  br              ON br.brand_id   = bbsp.brand_id
            JOIN spare_parts sp         ON sp.spareparts_id = bbsp.spareparts_id
            JOIN spare_part_names spn   ON spn.spareparts_id = sp.spareparts_id
            WHERE br.brand_id = :brandId
              AND b.location  ILIKE :city
              AND sp.spareparts_type ILIKE :spType
              AND sp.condition       ILIKE :cond
              AND spn.spareparts_name IN (:names)
            GROUP BY b.branch_id, b.branch_name, b.branch_address, br.brand_name, b.location""",
            nativeQuery = true)
    List<Object[]> searchBranchesNative(@Param("brandId") Long brandId,
                                        @Param("city") String city,
                                        @Param("spType") String sparePartType,
                                        @Param("cond") String condition,
                                        @Param("names") List<String> sparePartNames);
}

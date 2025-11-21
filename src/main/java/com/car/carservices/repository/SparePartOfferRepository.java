// src/main/java/com/car/carservices/repository/SparePartOfferRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.SparePartsRequest;
import com.car.carservices.repository.views.SparePartOfferView;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartOfferRepository extends JpaRepository<SparePartsRequest, Long> {

    @Query(value = """
        SELECT
            spr.sparepartsrequest_id          AS sparePartsRequestId,
            spr.date                          AS date,
            b.branch_name                     AS branchName,
            b.address                         AS address,
            b.city                            AS city,
            spr.vin_number                    AS vin,
            sp.spareparts_type                AS sparepartsType,
            bbsp.state                        AS state,
            sprd.spare_part                   AS sparePart,
            sprd.class_type                   AS classType,
            sprd.qty                          AS qty,
            sprd.price                        AS price,
            c.manager_mobile                  AS managerMobile,
            bbsp.id                           AS id,
            spr.request_status                AS requestStatus,
            sprd.id                           AS detailId,
            br.brand_id                       AS brandId,     -- NEW
            br.brand_name                     AS brandName    -- NEW
        FROM spare_parts_request spr
        JOIN spare_parts             sp   ON sp.spareparts_id = spr.spareparts_id
        JOIN branch_brand_spare_part bbsp ON bbsp.branch_id = spr.branch_id
        JOIN branch                  b    ON b.branch_id     = bbsp.branch_id
        JOIN company                 c    ON c.company_id    = b.company_id
        JOIN spare_parts_request_details sprd ON sprd.sparepartsrequest_id = spr.sparepartsrequest_id
        JOIN brand                   br   ON br.brand_id     = bbsp.brand_id     -- NEW JOIN
        WHERE spr.user_id = :userId
        ORDER BY spr.date DESC, b.branch_name ASC
        """, nativeQuery = true)
    List<SparePartOfferView> findOffersByUserId(@Param("userId") Long userId);

    @Query(value = """
        SELECT
            spr.sparepartsrequest_id          AS sparePartsRequestId,
            spr.date                          AS date,
            b.branch_name                     AS branchName,
            b.address                         AS address,
            b.city                            AS city,
            spr.vin_number                    AS vin,
            sp.spareparts_type                AS sparepartsType,
            bbsp.state                        AS state,
            sprd.spare_part                   AS sparePart,
            sprd.class_type                   AS classType,
            sprd.qty                          AS qty,
            sprd.price                        AS price,
            c.manager_mobile                  AS managerMobile,
            bbsp.id                           AS id,
            spr.request_status                AS requestStatus,
            sprd.id                           AS detailId,
            br.brand_id                       AS brandId,     -- NEW
            br.brand_name                     AS brandName    -- NEW
        FROM spare_parts_request spr
        JOIN spare_parts             sp   ON sp.spareparts_id = spr.spareparts_id
        JOIN branch_brand_spare_part bbsp ON bbsp.branch_id = spr.branch_id
        JOIN branch                  b    ON b.branch_id     = bbsp.branch_id
        JOIN company                 c    ON c.company_id    = b.company_id
        JOIN spare_parts_request_details sprd ON sprd.sparepartsrequest_id = spr.sparepartsrequest_id
        JOIN brand                   br   ON br.brand_id     = bbsp.brand_id     -- NEW JOIN
        WHERE spr.user_id = :userId
          AND b.branch_id = :branchId
        ORDER BY spr.date DESC, b.branch_name ASC
        """, nativeQuery = true)
    List<SparePartOfferView> findOffersByUserAndBranch(@Param("userId") Long userId,
                                                       @Param("branchId") Long branchId);

    // NEW: by BRANCH only (no user filter)
    @Query(value = """
        SELECT
            spr.sparepartsrequest_id          AS sparePartsRequestId,
            spr.date                          AS date,
            b.branch_name                     AS branchName,
            b.address                         AS address,
            b.city                            AS city,
            spr.vin_number                    AS vin,
            sp.spareparts_type                AS sparepartsType,
            bbsp.state                        AS state,
            sprd.spare_part                   AS sparePart,
            sprd.class_type                   AS classType,
            sprd.qty                          AS qty,
            sprd.price                        AS price,
            c.manager_mobile                  AS managerMobile,
            bbsp.id                           AS id,
            spr.request_status                AS requestStatus,
            sprd.id                           AS detailId,
            br.brand_id                       AS brandId,
            br.brand_name                     AS brandName
        FROM spare_parts_request spr
        JOIN spare_parts             sp   ON sp.spareparts_id = spr.spareparts_id
        JOIN branch_brand_spare_part bbsp ON bbsp.branch_id = spr.branch_id
        JOIN branch                  b    ON b.branch_id     = bbsp.branch_id
        JOIN company                 c    ON c.company_id    = b.company_id
        JOIN spare_parts_request_details sprd ON sprd.sparepartsrequest_id = spr.sparepartsrequest_id
        JOIN brand                   br   ON br.brand_id     = bbsp.brand_id
        WHERE b.branch_id = :branchId
        ORDER BY spr.date DESC, b.branch_name ASC
        """, nativeQuery = true)
    List<SparePartOfferView> findOffersByBranch(@Param("branchId") Long branchId);
}

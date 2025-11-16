// src/main/java/com/car/carservices/repository/BranchBrandServiceReadRepository.java
package com.car.carservices.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BranchBrandServiceReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public BranchBrandServiceReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Row projection with brand/service details */
    public record BrandServiceRow(
            Long brandId,
            String brandName,
            String brandStatus,
            String brandIcon,
            Long branchBrandServiceId,
            String itemStatus,
            String serviceName
    ) {}

    public List<BrandServiceRow> findBrandServicesByBranchId(long branchId, Long brandId, Long serviceId) {
        StringBuilder sql = new StringBuilder("""
            SELECT  b.brand_id,
                    b.brand_name,
                    b.status                 AS brand_status,
                    b.brand_icon             AS brand_icon,
                    bbs.id                   AS branch_brand_service_id,
                    bbs.status               AS item_status,
                    s.service_name
            FROM branch_brand_service bbs
            JOIN brand b           ON bbs.brand_id   = b.brand_id
            JOIN service_entity s  ON bbs.service_id = s.service_id
            WHERE bbs.branch_id = :branchId
        """);

        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);

        if (brandId != null) {
            sql.append(" AND bbs.brand_id = :brandId");
            params.put("brandId", brandId);
        }
        if (serviceId != null) {
            sql.append(" AND bbs.service_id = :serviceId");
            params.put("serviceId", serviceId);
        }

        sql.append(" ORDER BY b.brand_name, s.service_name");

        return jdbc.query(
                sql.toString(),
                params,
                (rs, i) -> new BrandServiceRow(
                        rs.getLong("brand_id"),
                        rs.getString("brand_name"),
                        rs.getString("brand_status"),
                        rs.getString("brand_icon"),
                        rs.getLong("branch_brand_service_id"),
                        rs.getString("item_status"),
                        rs.getString("service_name")
                )
        );
    }
}

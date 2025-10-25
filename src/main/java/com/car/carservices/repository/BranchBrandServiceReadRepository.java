package com.car.carservices.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BranchBrandServiceReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public BranchBrandServiceReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Row projection: brand data + (branch_brand_service.id, service_entity.service_name) */
    public record BrandServiceRow(
            Long brandId,
            String brandName,
            String status,
            Long branchBrandServiceId,   // <-- bbs.id
            String serviceName           // <-- s.service_name
    ) {}

    public List<BrandServiceRow> findBrandServicesByBranchId(long branchId) {
        final String sql = """
            SELECT  b.brand_id,
                    b.brand_name,
                    b.status,
                    bbs.id           AS branch_brand_service_id,
                    s.service_name
            FROM branch_brand_service bbs
            JOIN brand b           ON bbs.brand_id  = b.brand_id
            JOIN service_entity s  ON bbs.service_id = s.service_id
            WHERE bbs.branch_id = :branchId
            ORDER BY b.brand_name, s.service_name
            """;
        return jdbc.query(
            sql,
            Map.of("branchId", branchId),
            (rs, i) -> new BrandServiceRow(
                rs.getLong("brand_id"),
                rs.getString("brand_name"),
                rs.getString("status"),
                rs.getLong("branch_brand_service_id"),
                rs.getString("service_name")
            )
        );
    }
}

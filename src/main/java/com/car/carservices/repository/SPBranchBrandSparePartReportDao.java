package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SPBranchBrandSparePartReportDao {

    private final JdbcTemplate jdbcTemplate;

    public SPBranchBrandSparePartReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Row> findRowsByBranchId(Long branchId) {

        String sql = """
            SELECT
                bbsp.id                    AS id,
                bbsp.branch_id             AS branch_id,
                bbsp.brand_id              AS brand_id,
                b.brand_name               AS brand_name,
                bbsp.status                AS status,
                bbsp.spareparts_id         AS spareparts_id,
                sp.spareparts_type         AS spareparts_type,
                bbsp.state                 AS state
            FROM branch_brand_spare_part bbsp
            JOIN brand b
              ON b.brand_id = bbsp.brand_id
            JOIN spare_parts sp
              ON sp.spareparts_id = bbsp.spareparts_id
            WHERE bbsp.branch_id = ?
            ORDER BY bbsp.brand_id, bbsp.id
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Row(
                rs.getLong("id"),
                rs.getLong("branch_id"),
                rs.getLong("brand_id"),
                rs.getString("brand_name"),
                rs.getString("status"),
                rs.getLong("spareparts_id"),
                rs.getString("spareparts_type"),
                rs.getString("state")
        ), branchId);
    }

    public record Row(
            Long id,
            Long branchId,
            Long brandId,
            String brandName,
            String status,
            Long sparepartsId,
            String sparepartsType,
            String state
    ) {}
}
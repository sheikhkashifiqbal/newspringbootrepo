package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BABranchBrandSparePartDao {

    private final JdbcTemplate jdbcTemplate;

    public BABranchBrandSparePartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int updateStatusByBranchAndBrand(Long branchId, Long brandId, String status) {
        String sql = """
            UPDATE branch_brand_spare_part
            SET status = ?
            WHERE branch_id = ?
              AND brand_id = ?
        """;
        return jdbcTemplate.update(sql, status, branchId, brandId);
    }

    public boolean existsForBranchAndBrand(Long branchId, Long brandId) {
        String sql = """
            SELECT EXISTS(
                SELECT 1 FROM branch_brand_spare_part
                WHERE branch_id = ?
                  AND brand_id = ?
            )
        """;
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, branchId, brandId);
        return Boolean.TRUE.equals(exists);
    }
}
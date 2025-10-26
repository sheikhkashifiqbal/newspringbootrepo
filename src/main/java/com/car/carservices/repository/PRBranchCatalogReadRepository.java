package com.car.carservices.repository;

import com.car.carservices.dto.PRBrandDTO;
import com.car.carservices.dto.PRModelDTO;
import com.car.carservices.dto.PRServiceDTO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PRBranchCatalogReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PRBranchCatalogReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Distinct brands available for a branch where bbs.status='active' */
    public List<PRBrandDTO> findBrandsByBranch(Long branchId) {
        final String sql = """
            SELECT DISTINCT b.brand_id, b.brand_name
              FROM branch_brand_service bbs
              JOIN brand b ON bbs.brand_id = b.brand_id
             WHERE bbs.branch_id = :branchId
               AND bbs.status = 'active'
             ORDER BY b.brand_name
            """;
        return jdbc.query(sql, Map.of("branchId", branchId),
                (rs, i) -> new PRBrandDTO(
                        rs.getLong("brand_id"),
                        rs.getString("brand_name")));
    }

    /** Distinct services available for a branch where bbs.status='active' */
    public List<PRServiceDTO> findServicesByBranch(Long branchId) {
        final String sql = """
            SELECT DISTINCT s.service_id, s.service_name
              FROM branch_brand_service bbs
              JOIN service_entity s ON bbs.service_id = s.service_id
             WHERE bbs.branch_id = :branchId
               AND bbs.status = 'active'
             ORDER BY s.service_name
            """;
        return jdbc.query(sql, Map.of("branchId", branchId),
                (rs, i) -> new PRServiceDTO(
                        rs.getLong("service_id"),
                        rs.getString("service_name")));
    }

    /** All models whose brand_id is among the active brands for this branch */
    public List<PRModelDTO> findModelsByBranch(Long branchId) {
        final String sql = """
            SELECT DISTINCT cbm.id AS model_id, cbm.model_name
              FROM car_brand_model cbm
             WHERE cbm.brand_id IN (
                   SELECT DISTINCT bbs.brand_id
                     FROM branch_brand_service bbs
                    WHERE bbs.branch_id = :branchId
                      AND bbs.status = 'active'
             )
             ORDER BY cbm.model_name
            """;
        return jdbc.query(sql, Map.of("branchId", branchId),
                (rs, i) -> new PRModelDTO(
                        rs.getLong("model_id"),
                        rs.getString("model_name")));
    }
}

package com.car.carservices.repository;

import com.car.carservices.dto.SparePartsResponseDTO.SparePartItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BranchAndBrandSparePartsRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public BranchAndBrandSparePartsRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<SparePartItem> findActiveSparePartsByBranchAndBrand(Long branchId, Long brandId) {
        String sql = """
            SELECT sp.spareparts_id, sp.spareparts_type
            FROM branch_brand_spare_part bbsp
            JOIN spare_parts sp ON bbsp.spareparts_id = sp.spareparts_id
            JOIN branch b ON bbsp.branch_id = b.branch_id
            WHERE bbsp.branch_id = :branchId
              AND bbsp.brand_id = :brandId
              AND b.status = 'approved'
              
            ORDER BY sp.spareparts_type
        """;

        Map<String, Object> params = Map.of(
            "branchId", branchId,
            "brandId", brandId
        );

        return jdbc.query(sql, params, (rs, i) ->
                new SparePartItem(
                        rs.getLong("spareparts_id"),
                        rs.getString("spareparts_type")
                )
        );
    }
}
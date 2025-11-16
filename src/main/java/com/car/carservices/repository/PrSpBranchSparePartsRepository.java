package com.car.carservices.repository;

import com.car.carservices.dto.PrSpBranchSparePartsResponse;
import com.car.carservices.dto.PrSpBranchSparePartsResponse.BrandItem;
import com.car.carservices.dto.PrSpBranchSparePartsResponse.SparePartItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PrSpBranchSparePartsRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PrSpBranchSparePartsRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<PrSpBranchSparePartsResponse> findSpareParts(Long branchId, Long brandId) {
        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT 
                   b.branch_address, b.city, b.location, 
                   b.mobile AS manager_mobile, b.branch_code AS manager_phone,
                   br.brand_name, br.brand_icon,
                   sp.spareparts_id, sp.spareparts_type
            FROM branch_brand_spare_part bbsp
            JOIN branch b ON bbsp.branch_id = b.branch_id
            JOIN brand br ON bbsp.brand_id = br.brand_id
            JOIN spare_parts sp ON bbsp.spareparts_id = sp.spareparts_id
            WHERE b.status = 'approved'
        """);

        Map<String, Object> params = new HashMap<>();
        if (branchId != null) {
            sql.append(" AND bbsp.branch_id = :branchId");
            params.put("branchId", branchId);
        }
        if (brandId != null) {
            sql.append(" AND bbsp.brand_id = :brandId");
            params.put("brandId", brandId);
        }

        sql.append(" ORDER BY br.brand_name, sp.spareparts_type");

        List<Map<String, Object>> rows = jdbc.queryForList(sql.toString(), params);
        Map<String, BrandItem> brandMap = new LinkedHashMap<>();

        String branchAddress = null;
        String city = null;
        String location = null;
        String managerMobile = null;
        String managerPhone = null;

        for (Map<String, Object> row : rows) {
            branchAddress = (String) row.get("branch_address");
            city = (String) row.get("city");
            location = (String) row.get("location");
            managerMobile = (String) row.get("manager_mobile");
            managerPhone = (String) row.get("manager_phone");

            String brandName = (String) row.get("brand_name");
            String brandIcon = (String) row.get("brand_icon");

            brandMap.computeIfAbsent(brandName, k ->
                new BrandItem(brandName, brandIcon, new ArrayList<>())
            );

            List<SparePartItem> parts = brandMap.get(brandName).getAvailableSpareparts();
            String type = (String) row.get("spareparts_type");

            if (parts.stream().noneMatch(s -> s.getSparepartsType().equalsIgnoreCase(type))) {
                parts.add(new SparePartItem(
                    ((Number) row.get("spareparts_id")).longValue(),
                    type
                ));
            }
        }

        return List.of(new PrSpBranchSparePartsResponse(
            branchAddress, city, location, managerMobile, managerPhone,
            new ArrayList<>(brandMap.values())
        ));
    }
}

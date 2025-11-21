package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class SparePartsBranchRepo {

    private final JdbcTemplate jdbc;

    public SparePartsBranchRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findByBranch(Long branchId) {

        String sql = """
            SELECT 
                r.sparepartsrequest_id,
                r.date,
                r.VIN,
                r.spareparts_type,
                r.state,
                r.request_status,
                b.branch_name,
                b.address,
                b.city,
                b.manager_mobile,
                bbsp.brand_id,
                br.brand_name
            FROM spareparts_request r
            JOIN branch b ON b.branch_id = r.branch_id
            JOIN branch_brand_spare_part bbsp ON bbsp.branch_id = r.branch_id
            JOIN brand br ON br.brand_id = bbsp.brand_id
            WHERE r.branch_id = ?
        """;

        return jdbc.queryForList(sql, branchId);
    }

    public List<Map<String, Object>> findSparePartsByRequest(Long sparepartsRequestId) {

        String sql = """
            SELECT id, sparepartsrequest_id, spare_part, class_type, qty, price
            FROM spareparts_request_details
            WHERE sparepartsrequest_id = ?
        """;

        return jdbc.queryForList(sql, sparepartsRequestId);
    }
}

// src/main/java/com/car/carservices/repository/PRSlotNativeRepo.java
package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class PRSlotNativeRepo {

    private final JdbcTemplate jdbc;

    public PRSlotNativeRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Returns one row with keys: status, from_time, to_time; or null if none. */
    public Map<String, Object> findWorkDayRow(Long branchId, String workingDayUpper) {
        // Using column aliases to avoid reserved words in Java map keys
        String sql = """
            SELECT status,
                   "from" AS from_time,
                   "to"   AS to_time
            FROM work_days
            WHERE branch_id = ?
              AND UPPER(working_day) = ?
            LIMIT 1
        """;
        List<Map<String, Object>> rows = jdbc.queryForList(sql, branchId, workingDayUpper);
        return rows.isEmpty() ? null : rows.get(0);
    }

    /** Sum qty across branch_brand_service with optional filters. Returns 0 if none. */
    public int sumCapacityQty(Long branchId, Long brandId, Long serviceId) {
        StringBuilder sb = new StringBuilder("""
            SELECT COALESCE(SUM(qty), 0) AS total_qty
            FROM branch_brand_service
            WHERE branch_id = ?
        """);
        new Object(){};
        new Object(){};
        new Object(){};

        // dynamic filters
        if (brandId != null) sb.append(" AND brand_id = ").append(brandId);
        if (serviceId != null) sb.append(" AND service_id = ").append(serviceId);

        Integer total = jdbc.queryForObject(sb.toString(), Integer.class, branchId);
        return total == null ? 0 : total;
    }

    /** Count reservations for a single time slot with optional filters. */
    public int countReservationsAt(Long branchId, LocalDate date, java.time.LocalTime time,
                                   Long brandId, Long serviceId) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) 
            FROM reservation_service_request
            WHERE branch_id = ?
              AND reservation_date = ?
              AND reservation_time = ?
             /* AND (reservation_status IS NULL OR LOWER(reservation_status) <> 'canceled') */
        """);
        // dynamic filters (only if provided)
        if (brandId != null) sql.append(" AND brand_id = ").append(brandId);
        if (serviceId != null) sql.append(" AND service_id = ").append(serviceId);

        Integer cnt = jdbc.queryForObject(sql.toString(),
                Integer.class, branchId, date, Time.valueOf(time));
        return cnt == null ? 0 : cnt;
    }
}

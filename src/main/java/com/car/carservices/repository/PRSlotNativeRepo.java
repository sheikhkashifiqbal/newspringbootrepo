// src/main/java/com/car/carservices/repository/PRSlotNativeRepo.java
package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public class PRSlotNativeRepo {

    private final JdbcTemplate jdbc;

    public PRSlotNativeRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Small holder for box/non-box counts at a given time slot. */
    public static class BoxNonBoxCount {
        private final int boxReserved;
        private final int nonBoxReserved;

        public BoxNonBoxCount(int boxReserved, int nonBoxReserved) {
            this.boxReserved = boxReserved;
            this.nonBoxReserved = nonBoxReserved;
        }

        public int getBoxReserved() {
            return boxReserved;
        }

        public int getNonBoxReserved() {
            return nonBoxReserved;
        }
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

    /**
     * Sum qty across branch_brand_service with optional filters.
     * Interpreted as total number of boxes in the workshop.
     * Returns 0 if none.
     */
    public int sumCapacityQty(Long branchId, Long brandId, Long serviceId) {
        StringBuilder sb = new StringBuilder("""
            SELECT COALESCE(SUM(qty), 0) AS total_qty
            FROM branch_brand_service
            WHERE branch_id = ?
        """);

        // dynamic filters
        if (brandId != null) sb.append(" AND brand_id = ").append(brandId);
        if (serviceId != null) sb.append(" AND service_id = ").append(serviceId);

        Integer total = jdbc.queryForObject(sb.toString(), Integer.class, branchId);
        return total == null ? 0 : total;
    }

    /**
     * Sum experts across branch_brand_service with optional filters.
     * Interpreted as total number of experts available in the workshop.
     * Returns 0 if none.
     */
    public int sumExperts(Long branchId, Long brandId, Long serviceId) {
        StringBuilder sb = new StringBuilder("""
            SELECT COALESCE(SUM(experts), 0) AS total_experts
            FROM branch_brand_service
            WHERE branch_id = ?
        """);

        // dynamic filters
        if (brandId != null) sb.append(" AND brand_id = ").append(brandId);
        if (serviceId != null) sb.append(" AND service_id = ").append(serviceId);

        Integer total = jdbc.queryForObject(sb.toString(), Integer.class, branchId);
        return total == null ? 0 : total;
    }

    /**
     * Legacy method (no longer used in new logic) that counts all reservations
     * at a given time, ignoring BOX / NON-BOX distinction.
     */
    public int countReservationsAt(Long branchId, LocalDate date, LocalTime time,
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

        Integer cnt = jdbc.queryForObject(
                sql.toString(),
                Integer.class,
                branchId,
                date,
                Time.valueOf(time)
        );
        return cnt == null ? 0 : cnt;
    }

    /**
     * New method: count reservations at a time slot separated into BOX and NON-BOX
     * using service_entity.service_type.
     *
     * IMPORTANT CHANGE:
     *  - We NO LONGER filter by service_id here.
     *  - So boxReserved + nonBoxReserved = ALL reservations at that time,
     *    across all services. This is what enforces:
     *      "When all experts are busy, we cannot book ANY service."
     */
    public BoxNonBoxCount countReservationsByTypeAt(Long branchId,
                                                    LocalDate date,
                                                    LocalTime time,
                                                    Long brandId,
                                                    Long serviceId /* kept for signature compatibility, not used */) {

        StringBuilder sql = new StringBuilder("""
            SELECT 
                COALESCE(SUM(CASE WHEN UPPER(se.service_type) = 'BOX' 
                                  THEN 1 ELSE 0 END), 0) AS box_reserved,
                COALESCE(SUM(CASE WHEN UPPER(se.service_type) <> 'BOX' 
                                  THEN 1 ELSE 0 END), 0) AS non_box_reserved
            FROM reservation_service_request r
            JOIN service_entity se ON r.service_id = se.service_id
            WHERE r.branch_id = ?
              AND r.reservation_date = ?
              AND r.reservation_time = ?
             /* AND (r.reservation_status IS NULL OR LOWER(r.reservation_status) <> 'canceled') */
        """);

        // We allow brand filter (if you want capacity per brand),
        // but we DO NOT filter by service_id anymore for expert usage.
        if (brandId != null) {
            sql.append(" AND r.brand_id = ").append(brandId);
        }

        List<Map<String, Object>> rows = jdbc.queryForList(
                sql.toString(),
                branchId,
                date,
                Time.valueOf(time)
        );

        if (rows.isEmpty()) {
            return new BoxNonBoxCount(0, 0);
        }

        Map<String, Object> row = rows.get(0);
        int box = ((Number) row.get("box_reserved")).intValue();
        int nonBox = ((Number) row.get("non_box_reserved")).intValue();

        return new BoxNonBoxCount(box, nonBox);
    }

    /**
     * Get service_type (e.g. 'BOX', 'NON-BOX') for a given service_id.
     * Returns null if not found.
     */
    public String findServiceType(Long serviceId) {
        if (serviceId == null) {
            return null;
        }
        String sql = "SELECT UPPER(service_type) FROM service_entity WHERE service_id = ?";
        List<String> list = jdbc.queryForList(sql, String.class, serviceId);
        return list.isEmpty() ? null : list.get(0);
    }
}

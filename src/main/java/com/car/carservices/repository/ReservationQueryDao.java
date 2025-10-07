// src/main/java/com/car/carservices/repository/ReservationQueryDao.java
package com.car.carservices.repository;

import com.car.carservices.dto.ReservationSparePartLine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReservationQueryDao {

    private final JdbcTemplate jdbc;

    public ReservationQueryDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<ReservationRowRaw> findReservationsByUserId(Long userId) {
    String sql = """
        SELECT
            r.reservation_id           AS reservation_id,
            r.reservation_date         AS reservation_date,
            r.reservation_time         AS reservation_time,
            b.branch_name              AS branch_name,
            b.address                  AS address,
            b.city                     AS city,
            b.logo_img                 AS logo_img,              -- ⬅️ NEW
            br.brand_name              AS brand_name,
            cd.car_model               AS model_name,
            s.service_name             AS service_name,
            r.reservation_status       AS reservation_status,
            bbs.id                     AS branch_brand_serviceid,
            cd.plate_number            AS plate_number,
            COALESCE(AVG(re.stars), 0) AS stars
        FROM reservation_service_request r
        JOIN branch b ON b.branch_id = r.branch_id
        JOIN car_details cd ON cd.car_id = r.car_id
        JOIN brand br ON br.brand_id = cd.brand_id
        JOIN service_entity s ON s.service_id = r.service_id
        LEFT JOIN branch_brand_service bbs
          ON bbs.branch_id = r.branch_id
         AND bbs.service_id = r.service_id
         AND bbs.brand_id   = cd.brand_id
        LEFT JOIN rate_experience re
          ON re.branch_brand_serviceid = bbs.id
        WHERE r.user_id = ?
        GROUP BY r.reservation_id, r.reservation_date, r.reservation_time,
                 b.branch_name, b.address, b.city, b.logo_img,           -- ⬅️ add here
                 br.brand_name, cd.car_model, s.service_name,
                 r.reservation_status, bbs.id, cd.plate_number
        ORDER BY r.reservation_date DESC, r.reservation_time DESC
        """;
    return jdbc.query(sql, RES_ROW_MAPPER, userId);
}
    public Map<Long, List<ReservationSparePartLine>> findSparePartsForReservations(Collection<Long> reservationIds) {
    if (reservationIds == null || reservationIds.isEmpty()) return Map.of();

    String placeholders = String.join(",", java.util.Collections.nCopies(reservationIds.size(), "?"));
    String sql = """
        SELECT
          rss.reservation_service_sparepart_id,
          rss.part_name,
          rss.qty,
          rss.spareparts_id,
          sp.spareparts_type,                 -- ⬅️ NEW
          rss.reservation_id
        FROM reservation_service_sparepart rss
        LEFT JOIN spare_parts sp
          ON sp.spareparts_id = rss.spareparts_id   -- ⬅️ NEW
        WHERE rss.reservation_id IN (%s)
        ORDER BY rss.reservation_service_sparepart_id
        """.formatted(placeholders);

    Object[] args = reservationIds.toArray();

    java.util.List<ReservationSparePartLine> rows = jdbc.query(sql, args, (rs, i) -> new ReservationSparePartLine(
        rs.getLong("reservation_service_sparepart_id"),
        rs.getString("part_name"),
        (Integer) rs.getObject("qty"),
        (Long) rs.getObject("spareparts_id"),
        rs.getString("spareparts_type"),            // ⬅️ NEW
        rs.getLong("reservation_id")
    ));

    java.util.Map<Long, java.util.List<ReservationSparePartLine>> byRes = new java.util.LinkedHashMap<>();
    for (ReservationSparePartLine r : rows) {
        byRes.computeIfAbsent(r.reservationId(), k -> new java.util.ArrayList<>()).add(r);
    }
    return byRes;
}


    // --- Raw row holder + mapper ---
    public record ReservationRowRaw(
    Long reservationId,
    LocalDate reservationDate,
    LocalTime reservationTime,
    String branchName,
    String address,
    String city,
    String logoImg,            // ⬅️ NEW
    String brandName,
    String modelName,
    String serviceName,
    String reservationStatus,
    Long branchBrandServiceId,
    String plateNumber,
    Double stars
) {}
    private static final RowMapper<ReservationRowRaw> RES_ROW_MAPPER = (ResultSet rs, int rowNum) ->
    new ReservationRowRaw(
        rs.getLong("reservation_id"),
        rs.getObject("reservation_date", LocalDate.class),
        rs.getObject("reservation_time", LocalTime.class),
        rs.getString("branch_name"),
        rs.getString("address"),
        rs.getString("city"),
        rs.getString("logo_img"),            // ⬅️ NEW
        rs.getString("brand_name"),
        rs.getString("model_name"),
        rs.getString("service_name"),
        rs.getString("reservation_status"),
        (Long) rs.getObject("branch_brand_serviceid"),
        rs.getString("plate_number"),
        rs.getObject("stars") == null ? 0.0 : ((Number) rs.getObject("stars")).doubleValue()
    );
}

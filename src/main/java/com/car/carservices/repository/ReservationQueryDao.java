package com.car.carservices.repository;

import com.car.carservices.dto.ReservationSparePartLine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class ReservationQueryDao {

    private final JdbcTemplate jdbc;

    public ReservationQueryDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ============================================================
    //   API 1: /api/reservations/by-user
    // ============================================================
    public List<ReservationRowRaw> findReservationsByUserId(Long userId) {

        String sql = """
            SELECT
                r.user_id                  AS user_id,
                r.reservation_id           AS reservation_id,
                r.reservation_date         AS reservation_date,
                r.reservation_time         AS reservation_time,

                b.branch_name              AS branch_name,
                b.branch_address           AS address,       -- ✔ FIXED HERE
                b.city                     AS city,
                b.logo_img                 AS logo_img,

                br.brand_name              AS brand_name,
                m.model_name               AS model_name,     -- ✔ FIXED MODEL NAME
                s.service_name             AS service_name,
                r.reservation_status       AS reservation_status,

                bbs.id                     AS branch_brand_serviceid,
                cd.plate_number            AS plate_number,

                COALESCE(AVG(re.stars), 0) AS stars

            FROM reservation_service_request r

            JOIN branch b
              ON b.branch_id = r.branch_id

            LEFT JOIN car_details cd
              ON cd.car_id = r.car_id

            JOIN brand br
              ON br.brand_id = r.brand_id

            LEFT JOIN car_brand_model m
              ON m.id = r.model_id

            JOIN service_entity s
              ON s.service_id = r.service_id

            LEFT JOIN branch_brand_service bbs
              ON bbs.branch_id = r.branch_id
             AND bbs.service_id = r.service_id
             AND bbs.brand_id   = r.brand_id

            LEFT JOIN rate_experience re
              ON re.branch_brand_serviceid = bbs.id

            WHERE r.user_id = ?

            GROUP BY
                r.user_id,
                r.reservation_id, r.reservation_date, r.reservation_time,
                b.branch_name, b.branch_address, b.city, b.logo_img,
                br.brand_name, m.model_name, s.service_name,
                r.reservation_status, bbs.id, cd.plate_number

            ORDER BY r.reservation_date DESC, r.reservation_time DESC
            """;

        return jdbc.query(sql, ROW_MAPPER, userId);
    }

    // ============================================================
    //   API 2: /api/reservations/by-branch
    // ============================================================
    public List<ReservationRowRaw> findReservationsByBranchId(Long branchId) {

        String sql = """
            SELECT
                r.user_id                  AS user_id,
                r.reservation_id           AS reservation_id,
                r.reservation_date         AS reservation_date,
                r.reservation_time         AS reservation_time,

                b.branch_name              AS branch_name,
                b.branch_address           AS address,       -- ✔ FIXED HERE
                b.city                     AS city,
                b.logo_img                 AS logo_img,

                br.brand_name              AS brand_name,
                m.model_name               AS model_name,
                s.service_name             AS service_name,
                r.reservation_status       AS reservation_status,

                bbs.id                     AS branch_brand_serviceid,
                cd.plate_number            AS plate_number,

                COALESCE(AVG(re.stars), 0) AS stars

            FROM reservation_service_request r

            JOIN branch b
              ON b.branch_id = r.branch_id

            LEFT JOIN car_details cd
              ON cd.car_id = r.car_id

            JOIN brand br
              ON br.brand_id = r.brand_id

            LEFT JOIN car_brand_model m
              ON m.id = r.model_id

            JOIN service_entity s
              ON s.service_id = r.service_id

            LEFT JOIN branch_brand_service bbs
              ON bbs.branch_id = r.branch_id
             AND bbs.service_id = r.service_id
             AND bbs.brand_id   = r.brand_id

            LEFT JOIN rate_experience re
              ON re.branch_brand_serviceid = bbs.id

            WHERE r.branch_id = ?

            GROUP BY
                r.user_id,
                r.reservation_id, r.reservation_date, r.reservation_time,
                b.branch_name, b.branch_address, b.city, b.logo_img,
                br.brand_name, m.model_name, s.service_name,
                r.reservation_status, bbs.id, cd.plate_number

            ORDER BY r.reservation_date DESC, r.reservation_time DESC
            """;

        return jdbc.query(sql, ROW_MAPPER, branchId);
    }

    // ============================================================
    //   Spare Parts Loader
    // ============================================================
    public Map<Long, List<ReservationSparePartLine>> findSparePartsForReservations(Collection<Long> reservationIds) {
        if (reservationIds == null || reservationIds.isEmpty()) return Map.of();

        String placeholders = String.join(",", Collections.nCopies(reservationIds.size(), "?"));

        String sql = """
            SELECT
                rss.reservation_service_sparepart_id,
                rss.part_name,
                rss.qty,
                rss.spareparts_id,
                sp.spareparts_type,
                rss.reservation_id
            FROM reservation_service_sparepart rss
            LEFT JOIN spare_parts sp
              ON sp.spareparts_id = rss.spareparts_id
            WHERE rss.reservation_id IN (%s)
            ORDER BY rss.reservation_service_sparepart_id
            """.formatted(placeholders);

        Object[] args = reservationIds.toArray();

        List<ReservationSparePartLine> rows = jdbc.query(sql, args, (rs, i) ->
                new ReservationSparePartLine(
                        rs.getLong("reservation_service_sparepart_id"),
                        rs.getString("part_name"),
                        (Integer) rs.getObject("qty"),
                        (Long) rs.getObject("spareparts_id"),
                        rs.getString("spareparts_type"),
                        rs.getLong("reservation_id")
                )
        );

        Map<Long, List<ReservationSparePartLine>> map = new LinkedHashMap<>();
        for (ReservationSparePartLine r : rows) {
            map.computeIfAbsent(r.reservationId(), k -> new ArrayList<>()).add(r);
        }
        return map;
    }

    // ============================================================
    //   Row Record + Mapper
    // ============================================================

    public record ReservationRowRaw(
            Long userId,
            Long reservationId,
            LocalDate reservationDate,
            LocalTime reservationTime,
            String branchName,
            String address,
            String city,
            String logoImg,
            String brandName,
            String modelName,
            String serviceName,
            String reservationStatus,
            Long branchBrandServiceId,
            String plateNumber,
            Double stars
    ) {}

    private static final RowMapper<ReservationRowRaw> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new ReservationRowRaw(
                    rs.getLong("user_id"),
                    rs.getLong("reservation_id"),
                    rs.getObject("reservation_date", LocalDate.class),
                    rs.getObject("reservation_time", LocalTime.class),
                    rs.getString("branch_name"),
                    rs.getString("address"),          // ✔ returns branch_address
                    rs.getString("city"),
                    rs.getString("logo_img"),
                    rs.getString("brand_name"),
                    rs.getString("model_name"),
                    rs.getString("service_name"),
                    rs.getString("reservation_status"),
                    (Long) rs.getObject("branch_brand_serviceid"),
                    rs.getString("plate_number"),
                    rs.getObject("stars") == null ? 0.0 : ((Number) rs.getObject("stars")).doubleValue()
            );
}

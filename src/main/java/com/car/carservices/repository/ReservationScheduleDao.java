package com.car.carservices.repository;

import com.car.carservices.dto.BranchDayReservationItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Row for work_days
    public static class WorkDayRow {
        private final String workingDay;
        private final String from;
        private final String to;

        public WorkDayRow(String workingDay, String from, String to) {
            this.workingDay = workingDay;
            this.from = from;
            this.to = to;
        }

        public String getWorkingDay() {
            return workingDay;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

    private static final RowMapper<WorkDayRow> WORK_DAY_MAPPER = (ResultSet rs, int rowNum) ->
        new WorkDayRow(
            rs.getString("working_day"),
            rs.getString("from"),
            rs.getString("to")
        );

    /**
     * Fetch "from" / "to" from work_days table for given branch + working_day.
     */
    public WorkDayRow findWorkDay(Long branchId, String workingDay) {
        String sql = """
            SELECT working_day, "from", "to"
            FROM work_days
            WHERE branch_id = ?
              AND LOWER(working_day) = ?
              AND status = 'active'
            LIMIT 1
        """;
        String normalizedDay = workingDay.toLowerCase();
        List<WorkDayRow> list = jdbcTemplate.query(sql, WORK_DAY_MAPPER, branchId, normalizedDay);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Fetch reservations + joined info for given branch + date.
     */
    public List<BranchDayReservationItem> findReservationsForBranchAndDate(Long branchId, LocalDate date) {
        String sql = """
            SELECT
                r.reservation_id,
                r.reservation_date,
                r.reservation_time,
                r.brand_id,
                b.brand_name,
                r.model_id,
                m.model_name,
                
                'plate number' as plate_number,
                r.service_id,
                s.service_name,
                u.email
            FROM reservation_service_request r
            JOIN brand b
              ON b.brand_id = r.brand_id
            JOIN car_brand_model m
              ON m.id = r.model_id
            
            JOIN service_entity s
              ON s.service_id = r.service_id
            JOIN user_registration u
              ON u.id = r.user_id
            WHERE r.branch_id = ?
              AND r.reservation_date = ?
            ORDER BY r.reservation_time
        """;

        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            BranchDayReservationItem item = new BranchDayReservationItem();
            item.setReservationId(rs.getLong("reservation_id"));

            LocalDate d = rs.getObject("reservation_date", LocalDate.class);
            LocalTime t = rs.getObject("reservation_time", LocalTime.class);

            item.setReservationDate(d != null ? d.toString() : null);
            item.setReservationTime(t != null ? t.toString() : null);

            item.setBrandId(rs.getLong("brand_id"));
            item.setBrandName(rs.getString("brand_name"));
            item.setModelId(rs.getLong("model_id"));
            item.setModelName(rs.getString("model_name"));
            item.setPlateNumber(rs.getString("plate_number"));
            item.setServiceId(rs.getLong("service_id"));
            item.setServiceName(rs.getString("service_name"));
            item.setEmail(rs.getString("email"));
            return item;
        }, branchId, date);
    }
}

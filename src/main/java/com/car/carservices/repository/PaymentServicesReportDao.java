package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class PaymentServicesReportDao {

    private final JdbcTemplate jdbc;

    public PaymentServicesReportDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Returns totals per company + branch for the given month/year.
     * Sums lmps_service.price only for completed reservations,
     * matching service_id + city_id (branch.city_id) in lmps_service.
     *
     * Assumes Postgres; uses EXTRACT(MONTH/YEAR).
     */
    public List<BranchLevelRow> fetchBranchTotals(int month, int year) {
        String sql = """
            SELECT
                c.company_id                        AS company_id,
                c.company_name                      AS company_name,
                b.branch_id                         AS branch_id,
                b.branch_name                       AS branch_name,
                COALESCE(SUM(ls.price), 0)          AS total_earning,
                MAX(ls.currency)                    AS currency
            FROM reservation_service_request r
            JOIN branch b
              ON b.branch_id = r.branch_id
            JOIN company c
              ON c.company_id = b.company_id
            JOIN lmps_service ls
              ON ls.service_id = r.service_id
             AND ls.city_id = b.city_id
             AND LOWER(COALESCE(ls.status,'')) = 'active'
            WHERE LOWER(COALESCE(r.reservation_status,'')) = 'completed'
              AND EXTRACT(MONTH FROM r.reservation_date) = ?
              AND EXTRACT(YEAR  FROM r.reservation_date) = ?
            GROUP BY
                c.company_id, c.company_name,
                b.branch_id, b.branch_name
            ORDER BY b.branch_id
        """;

        return jdbc.query(sql, (rs, i) -> new BranchLevelRow(
                rs.getLong("company_id"),
                rs.getString("company_name"),
                rs.getLong("branch_id"),
                rs.getString("branch_name"),
                (BigDecimal) rs.getObject("total_earning"),
                rs.getString("currency")
        ), month, year);
    }

    public record BranchLevelRow(
            Long companyId,
            String companyName,
            Long branchId,
            String branchName,
            BigDecimal totalEarning,
            String currency
    ) {}
}
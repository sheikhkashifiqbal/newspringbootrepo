package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class SparePartsPaymentReportDao {

    private final JdbcTemplate jdbcTemplate;

    public SparePartsPaymentReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Postgres-safe query:
     * - column name "date" is quoted: r."date"
     * - month/year extracted from CAST(r."date" AS DATE)
     *
     * If your column type is already DATE/TIMESTAMP, CAST is still fine.
     */
    public List<Row> fetchBranchTotals(int month, int year) {

        String sql = """
            SELECT
                c.company_id                              AS company_id,
                c.company_name                            AS company_name,
                b.branch_id                               AS branch_id,
                b.branch_name                             AS branch_name,
                COALESCE(SUM(d.price), 0)                 AS total_earning,
                MAX(d.currency)                           AS currency
            FROM spare_parts_request r
            JOIN spare_parts_request_details d
              ON d.sparepartsrequest_id = r.sparepartsrequest_id
            JOIN branch b
              ON b.branch_id = r.branch_id
            JOIN company c
              ON c.company_id = b.company_id
            WHERE LOWER(COALESCE(r.request_status,'')) = 'accepted_offer'
              AND LOWER(COALESCE(d.payment_status,'')) = 'paid'
              AND EXTRACT(MONTH FROM CAST(r."date" AS DATE)) = ?
              AND EXTRACT(YEAR  FROM CAST(r."date" AS DATE)) = ?
            GROUP BY
                c.company_id, c.company_name,
                b.branch_id, b.branch_name
            ORDER BY b.branch_id
        """;

        return jdbcTemplate.query(sql, (rs, i) -> new Row(
                rs.getLong("company_id"),
                rs.getString("company_name"),
                rs.getLong("branch_id"),
                rs.getString("branch_name"),
                (BigDecimal) rs.getObject("total_earning"),
                rs.getString("currency")
        ), month, year);
    }

    public record Row(
            Long companyId,
            String companyName,
            Long branchId,
            String branchName,
            BigDecimal totalEarning,
            String currency
    ) {}
}
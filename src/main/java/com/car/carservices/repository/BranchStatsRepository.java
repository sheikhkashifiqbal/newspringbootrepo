package com.car.carservices.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class BranchStatsRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Base list of branches (company_id, branch_id) to drive LEFT JOIN behavior.
     * Optional filter by companyId (nullable).
     */
    public List<Object[]> fetchAllBranches(Long companyId) {
        String sql = """
            SELECT b.company_id, b.branch_id
            FROM branch b
            /**where**/
            ORDER BY b.company_id, b.branch_id
        """;
        if (companyId != null) {
            sql = sql.replace("/**where**/", "WHERE b.company_id = :companyId");
        } else {
            sql = sql.replace("/**where**/", "");
        }
        Query q = em.createNativeQuery(sql);
        if (companyId != null) q.setParameter("companyId", companyId);
        return q.getResultList(); // rows: [company_id(BigInt), branch_id(BigInt)]
    }

    /**
     * SUM of qty by branch from branch_brand_service.
     */
    public List<Object[]> fetchTotalQtyPerBranch(Long companyId) {
        String sql = """
            SELECT b.branch_id, COALESCE(SUM(bbs.qty), 0) AS total_qty
            FROM branch b
            LEFT JOIN branch_brand_service bbs ON bbs.branch_id = b.branch_id
            /**where**/
            GROUP BY b.branch_id
        """;
        if (companyId != null) {
            sql = sql.replace("/**where**/", "WHERE b.company_id = :companyId");
        } else {
            sql = sql.replace("/**where**/", "");
        }
        Query q = em.createNativeQuery(sql);
        if (companyId != null) q.setParameter("companyId", companyId);
        return q.getResultList(); // rows: [branch_id(BigInt), total_qty(BigInt)]
    }

    /**
     * COUNT of reservations by branch from reservation_service_request.
     * (Interpreting "total_reserve_service" as count of reservations branch-wise.)
     */
    public List<Object[]> fetchReservationCountPerBranch(Long companyId) {
        String sql = """
            SELECT b.branch_id, COALESCE(COUNT(rsr.reservation_id), 0) AS total_reserve
            FROM branch b
            LEFT JOIN reservation_service_request rsr ON rsr.branch_id = b.branch_id
            /**where**/
            GROUP BY b.branch_id
        """;
        if (companyId != null) {
            sql = sql.replace("/**where**/", "WHERE b.company_id = :companyId");
        } else {
            sql = sql.replace("/**where**/", "");
        }
        Query q = em.createNativeQuery(sql);
        if (companyId != null) q.setParameter("companyId", companyId);
        return q.getResultList(); // rows: [branch_id(BigInt), total_reserve(BigInt)]
    }

    /**
     * List of distinct service_ids per branch from branch_brand_service.
     */
    public List<Object[]> fetchServiceIdsPerBranch(Long companyId) {
        String sql = """
            SELECT b.branch_id,
                   COALESCE(ARRAY_AGG(DISTINCT bbs.service_id ORDER BY bbs.service_id), ARRAY[]::bigint[]) AS service_ids
            FROM branch b
            LEFT JOIN branch_brand_service bbs ON bbs.branch_id = b.branch_id
            /**where**/
            GROUP BY b.branch_id
        """;
        if (companyId != null) {
            sql = sql.replace("/**where**/", "WHERE b.company_id = :companyId");
        } else {
            sql = sql.replace("/**where**/", "");
        }
        Query q = em.createNativeQuery(sql);
        if (companyId != null) q.setParameter("companyId", companyId);
        return q.getResultList(); // rows: [branch_id(BigInt), service_ids(java.sql.Array)]
    }
}

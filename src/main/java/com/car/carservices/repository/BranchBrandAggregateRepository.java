package com.car.carservices.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BranchBrandAggregateRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Row> fetchRows() {
        String sql = """
            SELECT
                b.branch_id                       AS branchId,
                b.branch_name                     AS branchName,
                b.branch_cover_img                AS branchCoverImg,
                b.logo_img                        AS logoImg,
                br.brand_name                     AS brandName,
                AVG(rse.stars) OVER (PARTITION BY b.branch_id) AS avgStars
            FROM branch b
            LEFT JOIN branch_brand_spare_part bbsp
                   ON bbsp.branch_id = b.branch_id
            LEFT JOIN brand br
                   ON br.brand_id = bbsp.brand_id
            LEFT JOIN rate_sparepart_experience rse
                   ON rse.branch_brand_sparepart_id = bbsp.id
            ORDER BY b.branch_id
        """;

        @SuppressWarnings("unchecked")
        List<Object[]> data = em.createNativeQuery(sql).getResultList();

        List<Row> out = new ArrayList<>(data.size());
        for (Object[] r : data) {
            Long   branchId       = r[0] == null ? null : ((Number) r[0]).longValue();
            String branchName     = (String) r[1];
            String branchCoverImg = (String) r[2];
            String logoImg        = (String) r[3];
            String brandName      = (String) r[4];
            Double avgStars       = null;
            if (r[5] != null) {
                if (r[5] instanceof Number) avgStars = ((Number) r[5]).doubleValue();
                else try { avgStars = Double.valueOf(r[5].toString()); } catch (Exception ignore) {}
            }
            out.add(new Row(branchId, branchName, branchCoverImg, logoImg, brandName, avgStars));
        }
        return out;
    }

    public static class Row {
        public final Long branchId;
        public final String branchName;
        public final String branchCoverImg;
        public final String logoImg;
        public final String brandName; // may be null if no brand link
        public final Double avgStars;  // may be null if no ratings

        public Row(Long branchId, String branchName, String branchCoverImg,
                   String logoImg, String brandName, Double avgStars) {
            this.branchId = branchId;
            this.branchName = branchName;
            this.branchCoverImg = branchCoverImg;
            this.logoImg = logoImg;
            this.brandName = brandName;
            this.avgStars = avgStars;
        }
    }
}

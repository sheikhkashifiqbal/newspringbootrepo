package com.car.carservices.repository;

import com.car.carservices.dto.PRSparePartBranchItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.*;

@Repository
public class PRSparePartSearchReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PRSparePartSearchReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Map<String,Object>> findBrandByName(String brandName) {
        final String sql = """
            SELECT brand_id, brand_name
              FROM brand
             WHERE LOWER(brand_name) = LOWER(:bn)
             LIMIT 1
        """;
        var list = jdbc.queryForList(sql, Map.of("bn", brandName));
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public boolean modelExistsForBrand(Long brandId, String modelLike) {
        if (modelLike == null || modelLike.isBlank()) return true;
        final String sql = """
            SELECT 1
              FROM car_brand_model
             WHERE brand_id = :bid
               AND UPPER(model_name) LIKE CONCAT('%', :code, '%')
             LIMIT 1
        """;
        var rows = jdbc.query(sql, Map.of("bid", brandId, "code", modelLike.toUpperCase()), (rs,i)->1);
        return !rows.isEmpty();
    }

    public Optional<String> sparePartType(Long sparepartsId) {
        final String sql = """
            SELECT spareparts_type
              FROM spare_parts
             WHERE spareparts_id = :sid
             LIMIT 1
        """;
        var list = jdbc.query(sql, Map.of("sid", sparepartsId), (rs,i)->rs.getString("spareparts_type"));
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    /**
     * Branch rows (brand_id + spareparts_id [+ city]) including:
     *  - aggregated distinct states (from branch_brand_spare_part)
     *  - stars_mode: mode of stars from rate_sparepart_experience across matching bbsp ids
     *
     * Tie-break: higher stars value wins on same frequency (ORDER BY cnt DESC, stars DESC).
     */
    public List<Map<String,Object>> findBranchRowsWithStatesAndStars(Long brandId, Long sparepartsId, String cityOpt) {
        String base = """
            SELECT
                b.branch_id,
                b.branch_name,
                b.logo_img,
                b.latitude,
                b.longitude,
                ARRAY_AGG(DISTINCT LOWER(bbsp.state)) AS states,
                (
                  SELECT r.stars
                    FROM rate_sparepart_experience r
                   WHERE r.branch_brand_sparepart_id IN (
                         SELECT id
                           FROM branch_brand_spare_part
                          WHERE branch_id = b.branch_id
                            AND brand_id  = :bid
                            AND spareparts_id = :sid
                   )
                   GROUP BY r.stars
                   ORDER BY COUNT(*) DESC, r.stars DESC
                   LIMIT 1
                ) AS stars_mode
              FROM branch_brand_spare_part bbsp
              JOIN branch b ON b.branch_id = bbsp.branch_id
             WHERE bbsp.brand_id = :bid
               AND bbsp.spareparts_id = :sid
        """;

        String withCity = base + " AND LOWER(b.city) = LOWER(:city) ";
        String tail = " GROUP BY b.branch_id, b.branch_name, b.logo_img, b.latitude, b.longitude ORDER BY b.branch_name ";

        Map<String,Object> params = new HashMap<>();
        params.put("bid", brandId);
        params.put("sid", sparepartsId);

        String sql;
        if (cityOpt != null && !cityOpt.isBlank()) {
            params.put("city", cityOpt);
            sql = withCity + tail;
        } else {
            sql = base + tail;
        }

        return jdbc.query(sql, params, (rs, i) -> {
            Map<String,Object> row = new HashMap<>();
            row.put("branch_id", rs.getLong("branch_id"));
            row.put("branch_name", rs.getString("branch_name"));
            row.put("logo_img", rs.getString("logo_img"));
            row.put("latitude", (Double) rs.getObject("latitude"));
            row.put("longitude", (Double) rs.getObject("longitude"));

            // states array -> List<String>
            Array sqlArr = rs.getArray("states");
            List<String> states = new ArrayList<>();
            if (sqlArr != null) {
                Object arr = sqlArr.getArray();
                if (arr instanceof String[] sa) {
                    for (String s : sa) if (s != null) states.add(s.toLowerCase());
                } else if (arr instanceof Object[] oa) {
                    for (Object o : oa) if (o != null) states.add(o.toString().toLowerCase());
                }
            }
            row.put("states", states);

            Integer stars = (Integer) rs.getObject("stars_mode");
            row.put("stars_mode", stars); // nullable

            return row;
        });
    }
}

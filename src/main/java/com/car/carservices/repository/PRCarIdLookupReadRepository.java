package com.car.carservices.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class PRCarIdLookupReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PRCarIdLookupReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Find a car_id for a given user_id + brand_id + model_brand.
     * model_brand can match either car_model OR car_brand (case-insensitive), limited by user & brand.
     * Returns the first hit (if multiples exist).
     */
    public Optional<Long> findCarId(Long userId, Long brandId, String modelBrand) {
        final String sql = """
            SELECT cd.car_id
              FROM car_details cd
             WHERE cd.user_id  = :userId
               AND cd.brand_id = :brandId
               AND (
                     LOWER(cd.car_model) = LOWER(:mb)
                  OR LOWER(cd.car_brand) = LOWER(:mb)
               )
             ORDER BY cd.car_id
             LIMIT 1
            """;

        return jdbc.query(sql,
                Map.of("userId", userId, "brandId", brandId, "mb", modelBrand),
                rs -> rs.next() ? Optional.of(rs.getLong("car_id")) : Optional.empty());
    }
}

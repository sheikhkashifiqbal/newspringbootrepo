package com.car.carservices.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PRUserPlatesReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PRUserPlatesReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Returns raw plate_number values for a given user_id from car_details */
    public List<String> findPlatesByUserId(Long userId) {
        final String sql = """
            SELECT plate_number
              FROM car_details
             WHERE user_id = :userId
             ORDER BY plate_number
            """;
        return jdbc.query(sql, Map.of("userId", userId),
                (rs, i) -> rs.getString("plate_number"));
    }
}

package com.car.carservices.repository;

import com.car.carservices.dto.PRBrandDTO;
import com.car.carservices.dto.PRModelDTO;
import com.car.carservices.dto.PRCarLookupResponse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class PRCarLookupReadRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PRCarLookupReadRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Finds brand (by car_details.brand_id) and model (by matching car_details.car_model to car_brand_model.model_name,
     * case-insensitive, within the same brand_id), for a given plate_number.
     */
    public Optional<PRCarLookupResponse> findBrandAndModelByPlate(String plateNumber) {
        final String sql = """
            SELECT
                b.brand_id,
                b.brand_name,
                cbm.id          AS model_id,
                cbm.model_name  AS model_name
            FROM car_details cd
            LEFT JOIN brand b
                   ON b.brand_id = cd.brand_id
            LEFT JOIN car_brand_model cbm
                   ON cbm.brand_id = cd.brand_id
                  AND LOWER(cbm.model_name) = LOWER(cd.car_model)
            WHERE cd.plate_number = :plate
            LIMIT 1
        """;

        return jdbc.query(sql, Map.of("plate", plateNumber), rs -> {
            if (!rs.next()) return Optional.empty();

            Long brandId = (Long) rs.getObject("brand_id") == null ? null : rs.getLong("brand_id");
            String brandName = rs.getString("brand_name");
            Long modelId = (Long) rs.getObject("model_id") == null ? null : rs.getLong("model_id");
            String modelName = rs.getString("model_name");

            PRCarLookupResponse resp = new PRCarLookupResponse();
            if (brandId != null || brandName != null) {
                resp.setBrand(new PRBrandDTO(brandId, brandName));
            }
            if (modelId != null || modelName != null) {
                resp.setModel(new PRModelDTO(modelId, modelName));
            }
            return Optional.of(resp);
        });
    }
}

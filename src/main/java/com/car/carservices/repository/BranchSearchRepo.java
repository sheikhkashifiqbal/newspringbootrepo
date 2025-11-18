package com.car.carservices.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BranchSearchRepo {

    private final JdbcTemplate jdbc;

    public BranchSearchRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findBranches(Long brandId, Long serviceId, String location) {

        String sql = """
            SELECT b.branch_id, b.branch_name, b.city, b.logo_img, b.latitude, b.longitude
            FROM branch b
            JOIN branch_brand_service s ON s.branch_id = b.branch_id
            WHERE b.city ILIKE ?
              AND s.brand_id = ?
              AND s.service_id = ?
        """;

        return jdbc.queryForList(sql, location, brandId, serviceId);
    }

    public Double findBranchRating(Long branchId) {

        String sql = """
            SELECT r.stars
            FROM rate_experience r
            JOIN branch_brand_service bbs ON bbs.id = r.branch_brand_serviceid
            WHERE bbs.branch_id = ?
        """;

        List<Integer> starsList = jdbc.queryForList(sql, Integer.class, branchId);

        if (starsList.isEmpty()) return null;

        Map<Integer, Long> freq = starsList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey().doubleValue())
                .orElse(null);
    }
}

package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandAggregateResponse;
import com.car.carservices.repository.BranchBrandAggregateRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchBrandAggregateService {

    private final BranchBrandAggregateRepository repo;

    public BranchBrandAggregateService(BranchBrandAggregateRepository repo) {
        this.repo = repo;
    }

    public List<BranchBrandAggregateResponse> getBranchesWithBrandsAndStars() {
        var rows = repo.fetchRows();

        Map<Long, Agg> map = new LinkedHashMap<>();
        for (var r : rows) {
            var agg = map.computeIfAbsent(r.branchId,
                    id -> new Agg(r.branchName, r.branchCoverImg, r.logoImg));
            if (r.brandName != null && !r.brandName.isBlank()) {
                agg.brandNames.add(r.brandName);
            }
            if (agg.stars == null && r.avgStars != null) {
                agg.stars = r.avgStars;
            }
        }

        return map.values().stream()
                .map(Agg::toResponse)
                .collect(Collectors.toList());
    }

    private static class Agg {
        final String branchName;
        final String branchCoverImg;
        final String logoImg;
        final LinkedHashSet<String> brandNames = new LinkedHashSet<>();
        Double stars; // nullable

        Agg(String branchName, String branchCoverImg, String logoImg) {
            this.branchName = branchName;
            this.branchCoverImg = branchCoverImg;
            this.logoImg = logoImg;
        }

        BranchBrandAggregateResponse toResponse() {
            var brands = new ArrayList<>(brandNames);
            brands.sort(String.CASE_INSENSITIVE_ORDER);
            return new BranchBrandAggregateResponse(
                    branchName, brands, stars, branchCoverImg, logoImg
            );
        }
    }
}

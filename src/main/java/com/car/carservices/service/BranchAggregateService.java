package com.car.carservices.service;

import com.car.carservices.dto.BranchAggregateResponse;
import com.car.carservices.repository.BranchAggregateRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchAggregateService {

    private final BranchAggregateRepository repo;

    public BranchAggregateService(BranchAggregateRepository repo) {
        this.repo = repo;
    }

    public List<BranchAggregateResponse> fetchAllBranchesWithServices() {
        var rows = repo.fetchBranchServiceRows();

        Map<Long, Aggregator> map = new LinkedHashMap<>();

        for (var r : rows) {
            Aggregator agg = map.computeIfAbsent(
                r.branchId,
                id -> new Aggregator(r.branchName, r.branchCoverImg, r.logoImg)
            );
            if (r.serviceName != null && !r.serviceName.isBlank()) {
                agg.addService(r.serviceName);
            }
        }

        return map.values().stream()
                .map(Aggregator::toResponse)
                .collect(Collectors.toList());
    }

    private static class Aggregator {
        final String branchName;
        final String branchCoverImg;
        final String logoImg;
        final LinkedHashSet<String> services = new LinkedHashSet<>();

        Aggregator(String branchName, String branchCoverImg, String logoImg) {
            this.branchName = branchName;
            this.branchCoverImg = branchCoverImg;
            this.logoImg = logoImg;
        }

        void addService(String s) { services.add(s); }

        BranchAggregateResponse toResponse() {
            var list = new ArrayList<>(services);
            list.sort(String.CASE_INSENSITIVE_ORDER);
            return new BranchAggregateResponse(branchName, list, branchCoverImg, logoImg);
        }
    }
}

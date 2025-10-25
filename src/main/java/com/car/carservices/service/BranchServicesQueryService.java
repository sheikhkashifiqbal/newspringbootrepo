package com.car.carservices.service;

import com.car.carservices.dto.PRBranchServiceItem;
import com.car.carservices.dto.BranchServicesResponse;
import com.car.carservices.repository.BranchBrandServiceReadRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchServicesQueryService {

    private final BranchBrandServiceReadRepository repo;

    public BranchServicesQueryService(BranchBrandServiceReadRepository repo) {
        this.repo = repo;
    }

    public List<BranchServicesResponse> listBrandServicesForBranch(long branchId) {
        var rows = repo.findBrandServicesByBranchId(branchId);

        // Aggregate by brand, keep insertion order, and dedupe (by id) if necessary.
        record Agg(String brandName, String status, LinkedHashMap<Long, PRBranchServiceItem> items) {}
        Map<Long, Agg> grouped = new LinkedHashMap<>();

        for (var r : rows) {
            grouped.compute(r.brandId(), (id, agg) -> {
                if (agg == null) {
                    var map = new LinkedHashMap<Long, PRBranchServiceItem>();
                    map.put(r.branchBrandServiceId(), new PRBranchServiceItem(r.branchBrandServiceId(), r.serviceName()));
                    return new Agg(r.brandName(), r.status(), map);
                } else {
                    // only put if not already there (dedupe)
                    agg.items().putIfAbsent(r.branchBrandServiceId(), new PRBranchServiceItem(r.branchBrandServiceId(), r.serviceName()));
                    return agg;
                }
            });
        }

        return grouped.values().stream()
                .map(a -> new BranchServicesResponse(
                        a.brandName(),
                        a.status(),
                        new ArrayList<>(a.items().values())
                ))
                .collect(Collectors.toList());
    }
}

// src/main/java/com/yourapp/branchservices/service/BranchServicesQueryService.java
package com.car.carservices.service;

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

        // Group by brand_id to avoid collisions; remember brand_name + status per brand
        record Agg(String brandName, String status, LinkedHashSet<String> services) {}
        Map<Long, Agg> grouped = new LinkedHashMap<>();

        for (var r : rows) {
            grouped.compute(r.brandId(), (id, agg) -> {
                if (agg == null) {
                    var set = new LinkedHashSet<String>();
                    set.add(r.serviceName());
                    return new Agg(r.brandName(), r.status(), set);
                } else {
                    agg.services().add(r.serviceName());
                    return agg;
                }
            });
        }

        return grouped.values().stream()
                .map(a -> new BranchServicesResponse(a.brandName(), a.status(), new ArrayList<>(a.services())))
                .collect(Collectors.toList());
    }
}
       
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

        // Keep per-brand aggregation with item map (id -> item)
        record Agg(String brandName, String brandStatus,
                   LinkedHashMap<Long, PRBranchServiceItem> items) {}
        Map<Long, Agg> grouped = new LinkedHashMap<>(); // key = brandId

        for (var r : rows) {
            grouped.compute(r.brandId(), (id, agg) -> {
                if (agg == null) {
                    var map = new LinkedHashMap<Long, PRBranchServiceItem>();
                    map.put(
                        r.branchBrandServiceId(),
                        new PRBranchServiceItem(
                            r.branchBrandServiceId(),
                            r.serviceName(),
                            r.itemStatus() == null ? "active" : r.itemStatus().toLowerCase()
                        )
                    );
                    return new Agg(r.brandName(), r.brandStatus(), map);
                } else {
                    agg.items().putIfAbsent(
                        r.branchBrandServiceId(),
                        new PRBranchServiceItem(
                            r.branchBrandServiceId(),
                            r.serviceName(),
                            r.itemStatus() == null ? "active" : r.itemStatus().toLowerCase()
                        )
                    );
                    return agg;
                }
            });
        }

        // CHANGED: iterate entries so we can include the brandId (map key) in the DTO
        return grouped.entrySet().stream()
            .map(e -> {
                Long bId = e.getKey();
                Agg a = e.getValue();
                return new BranchServicesResponse(
                    bId,                         // <-- brand_id
                    a.brandName(),               // brand_name
                    a.brandStatus(),             // status (brand status)
                    new ArrayList<>(a.items().values())
                );
            })
            .collect(Collectors.toList());
    }
}

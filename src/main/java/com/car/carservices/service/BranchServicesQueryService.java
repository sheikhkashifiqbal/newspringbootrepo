// src/main/java/com/car/carservices/service/BranchServicesQueryService.java
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

    public List<BranchServicesResponse> listBrandServicesForBranch(long branchId, Long brandId, Long serviceId) {
        var rows = repo.findBrandServicesByBranchId(branchId, brandId, serviceId);

        record Agg(String brandName, String brandStatus, String brandIcon,
                   LinkedHashMap<Long, PRBranchServiceItem> items) {}

        Map<Long, Agg> grouped = new LinkedHashMap<>();

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
                    return new Agg(r.brandName(), r.brandStatus(), r.brandIcon(), map); // ✅ includes brandIcon
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

        return grouped.entrySet().stream()
                .map(e -> new BranchServicesResponse(
                        e.getKey(),
                        e.getValue().brandName(),
                        e.getValue().brandIcon(), // ✅ NEW
                        e.getValue().brandStatus(),
                        new ArrayList<>(e.getValue().items().values())
                ))
                .collect(Collectors.toList());
    }
}

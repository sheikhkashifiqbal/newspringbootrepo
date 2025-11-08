package com.car.carservices.service;

import com.car.carservices.dto.PRSparePartBranchItem;
import com.car.carservices.dto.PRSparePartSearchRequest;
import com.car.carservices.repository.PRSparePartSearchReadRepository;
import com.car.carservices.util.PRVinDecoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PRSparePartSearchService {

    private final PRSparePartSearchReadRepository repo;

    public PRSparePartSearchService(PRSparePartSearchReadRepository repo) {
        this.repo = repo;
    }

    public List<PRSparePartBranchItem> search(PRSparePartSearchRequest req) {
        if (req == null || req.getVin() == null || req.getVin().isBlank() || req.getSpareparts_id() == null) {
            return List.of();
        }

        String brandName = PRVinDecoder.brandFromVin(req.getVin());
        String modelCode = PRVinDecoder.modelCodeFromVin(req.getVin());
        if (brandName == null) return List.of();

        var brandOpt = repo.findBrandByName(brandName);
        if (brandOpt.isEmpty()) return List.of();
        Long brandId = ((Number) brandOpt.get().get("brand_id")).longValue();

        if (!repo.modelExistsForBrand(brandId, modelCode)) return List.of();

        String spareType = repo.sparePartType(req.getSpareparts_id()).orElse(null);

        List<Map<String,Object>> rows =
                repo.findBranchRowsWithStatesAndStars(brandId, req.getSpareparts_id(), req.getCity());

        Set<String> requested = (req.getState() == null || req.getState().isEmpty())
                ? null
                : req.getState().stream().filter(Objects::nonNull)
                      .map(s -> s.toLowerCase().trim()).collect(Collectors.toSet());

        List<PRSparePartBranchItem> result = new ArrayList<>();
        for (Map<String,Object> r : rows) {
            @SuppressWarnings("unchecked")
            List<String> available = (List<String>) r.getOrDefault("states", List.of());
            List<String> finalStates = (requested == null)
                    ? new ArrayList<>(available)
                    : available.stream().filter(requested::contains).sorted().toList();

            if (finalStates.isEmpty()) continue;

            Integer stars = (Integer) r.get("stars_mode"); // may be null if no ratings yet

            result.add(new PRSparePartBranchItem(
                    ((Number) r.get("branch_id")).longValue(),
                    (String) r.get("branch_name"),
                    (String) r.get("logo_img"),
                    finalStates,
                    spareType,
                    (Double) r.get("latitude"),
                    (Double) r.get("longitude"),
                    stars
            ));
        }
        return result;
    }
}

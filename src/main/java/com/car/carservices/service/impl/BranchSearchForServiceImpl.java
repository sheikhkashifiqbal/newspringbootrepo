package com.car.carservices.service.impl;

import com.car.carservices.dto.*;
import com.car.carservices.repository.BranchSearchRepo;
import com.car.carservices.service.PRTimeSlotService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BranchSearchForServiceImpl {

    private final BranchSearchRepo repo;
    private final PRTimeSlotService slotService;

    public BranchSearchForServiceImpl(BranchSearchRepo repo, PRTimeSlotService slotService) {
        this.repo = repo;
        this.slotService = slotService;
    }

    public List<BranchSearchResponse> search(BranchSearchRequest req) {

        List<Map<String, Object>> branches = repo.findBranches(
                req.getCarBrand(),
                req.getServiceEntity(),
                req.getLocation()
        );

        List<BranchSearchResponse> result = new ArrayList<>();

        for (var row : branches) {

            Long branchId = ((Number) row.get("branch_id")).longValue();

            // 🟢 Call existing "available-slots" logic exactly
            PRTimeSlotRequest slotReq = new PRTimeSlotRequest();
            slotReq.setBranch_id(branchId);
            slotReq.setBrand_id(req.getCarBrand());
            slotReq.setModel_id(req.getCarModel());
            slotReq.setService_id(req.getServiceEntity());
            slotReq.setDate(req.getDate());

            PRTimeSlotResponse slotResponse = slotService.getAvailableSlots(slotReq);

            BranchSearchResponse dto = new BranchSearchResponse();
            dto.setBranchId(branchId);
            dto.setBranchName((String) row.get("branch_name"));
            dto.setLocation((String) row.get("city"));
            dto.setCompanyLogoUrl((String) row.get("logo_img"));
            dto.setCompanyRating(repo.findBranchRating(branchId));
            dto.setDistanceKm(calculateDistanceKm(
                    req.getCurrentLat(), req.getCurrentLon(),
                    (Double) row.get("latitude"), (Double) row.get("longitude")
            ));
            dto.setAvailableTimeSlots(slotResponse.getAvailableTimeSlots());

            result.add(dto);
        }
        if (req.getSortBy() != null && !req.getSortBy().isBlank()) {
            switch (req.getSortBy().toUpperCase()) {

                case "RATING_HIGH_TO_LOW":
                    result.sort(Comparator.comparing(
                            BranchSearchResponse::getCompanyRating,
                            Comparator.nullsLast(Comparator.reverseOrder())
                    ));
                    break;

                case "RATING_LOW_TO_HIGH":
                    result.sort(Comparator.comparing(
                            BranchSearchResponse::getCompanyRating,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    ));
                    break;

                case "DISTANCE_CLOSEST":
                    result.sort(Comparator.comparing(
                            BranchSearchResponse::getDistanceKm,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    ));
                    break;

                case "DISTANCE_FARTHEST":
                    result.sort(Comparator.comparing(
                            BranchSearchResponse::getDistanceKm,
                            Comparator.nullsLast(Comparator.reverseOrder())
                    ));
                    break;

                default:
                    // No sorting applied if unknown value
                    break;
            }
        }



        return result;
    }

    private double calculateDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon/2) *
                Math.sin(dLon/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}

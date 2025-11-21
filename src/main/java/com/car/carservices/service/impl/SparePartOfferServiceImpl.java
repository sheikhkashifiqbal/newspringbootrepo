package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartLine;
import com.car.carservices.dto.SparePartOfferResponse;
import com.car.carservices.dto.SparePartOfferBranchResponse;
import com.car.carservices.repository.SparePartOfferRepository;
import com.car.carservices.repository.views.SparePartOfferView;
import com.car.carservices.service.SparePartOfferService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparePartOfferServiceImpl implements SparePartOfferService {

    private final SparePartOfferRepository repo;

    public SparePartOfferServiceImpl(SparePartOfferRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<SparePartOfferResponse> byUserId(Long userId) {
        if (userId == null) return List.of();
        return aggregateUser(repo.findOffersByUserId(userId));
    }

    @Override
    public List<SparePartOfferResponse> byUserAndBranch(Long userId, Long branchId) {
        if (userId == null || branchId == null) return List.of();
        return aggregateUser(repo.findOffersByUserAndBranch(userId, branchId));
    }

    @Override
    public List<SparePartOfferBranchResponse> byBranch(Long branchId) {
        if (branchId == null) return List.of();
        return aggregateBranch(repo.findOffersByBranch(branchId));
    }

    // =====================================================
    // FIXED duplicate spare_part lines using HashSet
    // =====================================================
    private static List<SparePartOfferResponse> aggregateUser(List<SparePartOfferView> rows) {
        Map<Long, Acc> map = new LinkedHashMap<>();

        for (SparePartOfferView r : rows) {
            Acc acc = map.computeIfAbsent(r.getSparePartsRequestId(), k ->
                new Acc(
                    r.getSparePartsRequestId(),
                    n(r.getDate()),
                    n(r.getBranchName()),
                    n(r.getAddress()),
                    n(r.getCity()),
                    n(r.getVin()),
                    n(r.getSparepartsType()),
                    n(r.getState()),
                    n(r.getManagerMobile()),
                    r.getId(),
                    n(r.getRequestStatus())
                )
            );

            // Deduplication check
            if (acc.addedIds.add(r.getDetailId())) {
                acc.lines.add(new SparePartLine(
                    r.getDetailId(),
                    r.getSparePartsRequestId(),
                    n(r.getSparePart()),
                    n(r.getClassType()),
                    r.getQty(),
                    r.getPrice()
                ));
            }
        }

        return map.values().stream().map(Acc::toResponse).toList();
    }


    private static List<SparePartOfferBranchResponse> aggregateBranch(List<SparePartOfferView> rows) {
        Map<Long, AccBranch> map = new LinkedHashMap<>();

        for (SparePartOfferView r : rows) {
            AccBranch acc = map.computeIfAbsent(r.getSparePartsRequestId(), k ->
                new AccBranch(
                    r.getSparePartsRequestId(),
                    r.getBrandId(),
                    n(r.getBrandName()),
                    n(r.getDate()),
                    n(r.getBranchName()),
                    n(r.getAddress()),
                    n(r.getCity()),
                    n(r.getVin()),
                    n(r.getSparepartsType()),
                    n(r.getState()),
                    n(r.getManagerMobile()),
                    r.getId(),
                    n(r.getRequestStatus())
                )
            );

            // Deduplication check
            if (acc.addedIds.add(r.getDetailId())) {
                acc.lines.add(new SparePartLine(
                    r.getDetailId(),
                    r.getSparePartsRequestId(),
                    n(r.getSparePart()),
                    n(r.getClassType()),
                    r.getQty(),
                    r.getPrice()
                ));
            }
        }

        return map.values().stream().map(AccBranch::toResponse).toList();
    }

    private static String n(String s) { return s == null ? "" : s; }

    // =====================================================
    // INTERNAL MAPPERS WITH DEDUPE TRACKING
    // =====================================================

    private static class Acc {
        final Long sparePartsRequestId;
        final String date, branchName, address, city, vin, sparepartsType, state, managerMobile, requestStatus;
        final Long bbspId;
        final List<SparePartLine> lines = new ArrayList<>();

        // 🔥 Deduplication set
        final Set<Long> addedIds = new HashSet<>();

        Acc(Long sprId, String date, String branchName, String address, String city, String vin,
            String sparepartsType, String state, String managerMobile, Long bbspId, String requestStatus) {
            this.sparePartsRequestId = sprId;
            this.date = date;
            this.branchName = branchName;
            this.address = address;
            this.city = city;
            this.vin = vin;
            this.sparepartsType = sparepartsType;
            this.state = state;
            this.managerMobile = managerMobile;
            this.bbspId = bbspId;
            this.requestStatus = requestStatus;
        }

        SparePartOfferResponse toResponse() {
            return new SparePartOfferResponse(
                sparePartsRequestId,
                date,
                branchName,
                address,
                city,
                vin,
                sparepartsType,
                state,
                List.copyOf(lines),
                managerMobile,
                bbspId,
                requestStatus
            );
        }
    }


    private static class AccBranch {
        final Long sparePartsRequestId, brandId, bbspId;
        final String brandName, date, branchName, address, city, vin, sparepartsType, state, managerMobile, requestStatus;
        final List<SparePartLine> lines = new ArrayList<>();

        // 🔥 Deduplication set
        final Set<Long> addedIds = new HashSet<>();

        AccBranch(Long sprId, Long brandId, String brandName, String date, String branchName,
                  String address, String city, String vin, String sparepartsType, String state,
                  String managerMobile, Long bbspId, String requestStatus) {
            this.sparePartsRequestId = sprId;
            this.brandId = brandId;
            this.brandName = brandName;
            this.date = date;
            this.branchName = branchName;
            this.address = address;
            this.city = city;
            this.vin = vin;
            this.sparepartsType = sparepartsType;
            this.state = state;
            this.managerMobile = managerMobile;
            this.bbspId = bbspId;
            this.requestStatus = requestStatus;
        }

        SparePartOfferBranchResponse toResponse() {
            return new SparePartOfferBranchResponse(
                sparePartsRequestId,
                brandId,
                brandName,
                date,
                branchName,
                address,
                city,
                vin,
                sparepartsType,
                state,
                List.copyOf(lines),
                managerMobile,
                bbspId,
                requestStatus
            );
        }
    }
}

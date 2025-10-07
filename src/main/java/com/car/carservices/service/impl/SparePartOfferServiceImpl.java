// src/main/java/com/car/carservices/service/impl/SparePartOfferServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartLine;
import com.car.carservices.dto.SparePartOfferResponse;
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
        return aggregate(repo.findOffersByUserId(userId));
    }

    @Override
    public List<SparePartOfferResponse> byUserAndBranch(Long userId, Long branchId) {
        if (userId == null || branchId == null) return List.of();
        return aggregate(repo.findOffersByUserAndBranch(userId, branchId));
    }

    private static List<SparePartOfferResponse> aggregate(List<SparePartOfferView> rows) {
        // group by request id (one top-level row per request)
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
            acc.lines.add(new SparePartLine(
                r.getDetailId(),
                r.getSparePartsRequestId(),
                n(r.getSparePart()),
                n(r.getClassType()),
                r.getQty(),
                r.getPrice()
            ));
        }
        List<SparePartOfferResponse> out = new ArrayList<>(map.size());
        for (Acc a : map.values()) out.add(a.toResponse());
        return out;
    }

    private static String n(String s) { return s == null ? "" : s; }

    private static class Acc {
        final Long sparePartsRequestId;
        final String date, branchName, address, city, vin, sparepartsType, state, managerMobile, requestStatus;
        final Long bbspId;
        final List<SparePartLine> lines = new ArrayList<>();

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
}

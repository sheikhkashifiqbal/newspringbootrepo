// src/main/java/com/car/carservices/service/ReservationQueryService.java
package com.car.carservices.service;

import com.car.carservices.dto.ReservationSparePartLine;
import com.car.carservices.dto.ReservationSummaryResponse;
import com.car.carservices.repository.ReservationQueryDao;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReservationQueryService {

    private final ReservationQueryDao repo;

    private static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ReservationQueryService(ReservationQueryDao repo) {
        this.repo = repo;
    }

    public List<ReservationSummaryResponse> byUserId(Long userId) {
        var rows = repo.findReservationsByUserId(userId);
        if (rows.isEmpty()) return List.of();

        var ids = rows.stream().map(ReservationQueryDao.ReservationRowRaw::reservationId).toList();

        Map<Long, List<ReservationSparePartLine>> partsByRes =
                repo.findSparePartsForReservations(ids);

        List<ReservationSummaryResponse> out = new ArrayList<>(rows.size());
        for (var r : rows) {
            String date = r.reservationDate() != null ? r.reservationDate().format(DATE) : null;
            String time = r.reservationTime() != null ? r.reservationTime().format(TIME) : null;
            int stars = (int) Math.round(Optional.ofNullable(r.stars()).orElse(0.0));

            out.add(new ReservationSummaryResponse(
                    r.userId(),                                // 🔹 NEW
                    date,
                    time,
                    r.branchName(),
                    r.address(),
                    r.city(),
                    r.logoImg(),
                    r.brandName(),
                    r.modelName(),
                    r.serviceName(),
                    r.reservationStatus(),
                    r.reservationId(),
                    r.branchBrandServiceId(),
                    r.plateNumber(),
                    stars,
                    partsByRes.getOrDefault(r.reservationId(), List.of())
            ));
        }
        return out;
    }

    // 🔹 used by POST /api/reservations/by-branch
    public List<ReservationSummaryResponse> byBranchId(Long branchId) {
        var rows = repo.findReservationsByBranchId(branchId);
        if (rows.isEmpty()) return List.of();

        var ids = rows.stream().map(ReservationQueryDao.ReservationRowRaw::reservationId).toList();

        Map<Long, List<ReservationSparePartLine>> partsByRes =
                repo.findSparePartsForReservations(ids);

        List<ReservationSummaryResponse> out = new ArrayList<>(rows.size());
        for (var r : rows) {
            String date = r.reservationDate() != null ? r.reservationDate().format(DATE) : null;
            String time = r.reservationTime() != null ? r.reservationTime().format(TIME) : null;
            int stars = (int) Math.round(Optional.ofNullable(r.stars()).orElse(0.0));

            out.add(new ReservationSummaryResponse(
                    r.userId(),                                // 🔹 NEW
                    date,
                    time,
                    r.branchName(),
                    r.address(),
                    r.city(),
                    r.logoImg(),
                    r.brandName(),
                    r.modelName(),
                    r.serviceName(),
                    r.reservationStatus(),
                    r.reservationId(),
                    r.branchBrandServiceId(),
                    r.plateNumber(),
                    stars,
                    partsByRes.getOrDefault(r.reservationId(), List.of())
            ));
        }
        return out;
    }
}

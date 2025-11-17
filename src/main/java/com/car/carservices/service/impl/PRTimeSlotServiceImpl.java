// src/main/java/com/car/carservices/service/impl/PRTimeSlotServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.PRTimeSlotRequest;
import com.car.carservices.dto.PRTimeSlotResponse;
import com.car.carservices.repository.PRSlotNativeRepo;
import com.car.carservices.service.PRTimeSlotService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PRTimeSlotServiceImpl implements PRTimeSlotService {

    private final PRSlotNativeRepo repo;

    public PRTimeSlotServiceImpl(PRSlotNativeRepo repo) {
        this.repo = repo;
    }

    @Override
    public PRTimeSlotResponse getAvailableSlots(PRTimeSlotRequest req) {
        if (req.getBranch_id() == null || req.getDate() == null || req.getDate().isBlank()) {
            return new PRTimeSlotResponse(List.of(), "branch_id and date are required");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(req.getDate()); // ISO yyyy-MM-dd
        } catch (Exception ex) {
            return new PRTimeSlotResponse(List.of(), "Invalid date format (use yyyy-MM-dd)");
        }

        // 1) Work day check
        DayOfWeek dow = date.getDayOfWeek(); // MONDAY..SUNDAY
        Map<String, Object> wd = repo.findWorkDayRow(req.getBranch_id(), dow.name());
        if (wd == null) {
            return new PRTimeSlotResponse(List.of(), "No service available");
        }
        String status = (String) wd.get("status");
        if (status == null || !status.equalsIgnoreCase("active")) {
            return new PRTimeSlotResponse(List.of(), "No service available");
        }

        java.sql.Time fromSql = (java.sql.Time) wd.get("from_time");
        java.sql.Time toSql   = (java.sql.Time) wd.get("to_time");
        if (fromSql == null || toSql == null) {
            return new PRTimeSlotResponse(List.of(), "No service available");
        }

        LocalTime from = fromSql.toLocalTime();
        LocalTime to   = toSql.toLocalTime();
        if (to.isBefore(from)) {
            // if data is bad, return nothing safely
            return new PRTimeSlotResponse(List.of(), "No service available");
        }

        // 2) Capacity from branch_brand_service
        // qty = number of boxes in the workshop
        int qtyCapacity = repo.sumCapacityQty(
                req.getBranch_id(),
                req.getBrand_id(),
                req.getService_id()
        );

        // experts = number of experts in the workshop
        int expertsCapacity = repo.sumExperts(
                req.getBranch_id(),
                req.getBrand_id(),
                req.getService_id()
        );

        // If no experts, no service can be provided
        if (expertsCapacity <= 0) {
            return new PRTimeSlotResponse(List.of(), "No service available");
        }

        // We tolerate qtyCapacity == 0 for NON-BOX services, since they don't use boxes.
        // For BOX services, qtyCapacity must be > 0 to ever have availability.

        // 3) Determine service type for capacity rule: BOX vs NON-BOX
        String serviceType = null;
        if (req.getService_id() != null) {
            serviceType = repo.findServiceType(req.getService_id());
        }
        if (serviceType != null) {
            serviceType = serviceType.toUpperCase();
        }

        // 4) Generate 30-min slots inclusive, then filter using new rules
        List<String> slots = new ArrayList<>();
        LocalTime t = from;
        while (!t.isAfter(to)) { // inclusive
            PRSlotNativeRepo.BoxNonBoxCount counts = repo.countReservationsByTypeAt(
                    req.getBranch_id(),
                    date,
                    t,
                    req.getBrand_id(),
                    req.getService_id()
            );

            int boxReserved = counts.getBoxReserved();
            int nonBoxReserved = counts.getNonBoxReserved();

            boolean canBook;

            if ("BOX".equals(serviceType)) {
                // BOX service: needs free expert AND free box
                canBook = canBookBox(boxReserved, nonBoxReserved, qtyCapacity, expertsCapacity);
            } else {
                // NON-BOX or unknown service_type: only constrained by experts
                canBook = canBookNonBox(boxReserved, nonBoxReserved, expertsCapacity);
            }

            if (canBook) {
                slots.add(formatHHmm(t));
            }

            t = t.plusMinutes(30);
        }

        return new PRTimeSlotResponse(slots, slots.isEmpty() ? "No service available" : "");
    }

    /**
     * BOX service: requires both boxes and experts.
     *
     * Rules (for "next" reservation):
     *  - boxReserved + 1 <= qtyCapacity
     *  - (boxReserved + nonBoxReserved) + 1 <= expertsCapacity
     */
    private boolean canBookBox(int boxReserved,
                               int nonBoxReserved,
                               int qtyCapacity,
                               int expertsCapacity) {

        // If there are no boxes configured at all, no BOX reservation possible.
        if (qtyCapacity <= 0) {
            return false;
        }

        int totalReserved = boxReserved + nonBoxReserved;

        // Check box availability
        if (boxReserved >= qtyCapacity) {
            return false;
        }

        // Check experts availability for the next reservation
        if (totalReserved >= expertsCapacity) {
            return false;
        }

        return true;
    }

    /**
     * NON-BOX service: only constrained by experts.
     *
     * Rule (for "next" reservation):
     *  - (boxReserved + nonBoxReserved) + 1 <= expertsCapacity
     */
    private boolean canBookNonBox(int boxReserved,
                                  int nonBoxReserved,
                                  int expertsCapacity) {

        int totalReserved = boxReserved + nonBoxReserved;

        // Check experts availability for the next reservation
        return totalReserved < expertsCapacity;
    }

    private static String formatHHmm(LocalTime t) {
        int h = t.getHour();
        int m = t.getMinute();
        return (h < 10 ? "0" + h : String.valueOf(h)) + ":" + (m < 10 ? "0" + m : String.valueOf(m));
    }
}

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

        // 2) Capacity (qty) from branch_brand_service
        int capacity = repo.sumCapacityQty(req.getBranch_id(), req.getBrand_id(), req.getService_id());
        if (capacity <= 0) {
            // No registration/qty configured => no slots
            return new PRTimeSlotResponse(List.of(), "No service available");
        }

        // 3) Generate 30-min slots inclusive, then filter by reservation load
        List<String> slots = new ArrayList<>();
        LocalTime t = from;
        while (!t.isAfter(to)) { // inclusive
            // Count existing reservations at this exact time (apply filters only if supplied)
            int booked = repo.countReservationsAt(
                    req.getBranch_id(),
                    date,
                    t,
                    req.getBrand_id(),
                    req.getService_id()
            );

            if (booked < capacity) {
                slots.add(formatHHmm(t));
            }
            t = t.plusMinutes(30);
        }

        return new PRTimeSlotResponse(slots, slots.isEmpty() ? "No service available" : "");
    }

    private static String formatHHmm(LocalTime t) {
        int h = t.getHour();
        int m = t.getMinute();
        return (h < 10 ? "0" + h : String.valueOf(h)) + ":" + (m < 10 ? "0" + m : String.valueOf(m));
    }
}

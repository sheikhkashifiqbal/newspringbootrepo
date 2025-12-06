package com.car.carservices.controller;

import com.car.carservices.dto.BranchDayScheduleRequest;
import com.car.carservices.dto.BranchDayScheduleResponse;
import com.car.carservices.service.ReservationScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-reservations")
public class ReservationScheduleController {

    private final ReservationScheduleService service;

    public ReservationScheduleController(ReservationScheduleService service) {
        this.service = service;
    }

    /**
     * POST /api/branch-reservations/day-schedule
     *
     * JSON Request:
     * {
     *   "branch_id": 1,
     *   "reservation_date": "2025-11-27"
     * }
     */
    @PostMapping("/day-schedule")
    public ResponseEntity<List<BranchDayScheduleResponse>> getDaySchedule(
            @RequestBody BranchDayScheduleRequest request) {

        List<BranchDayScheduleResponse> result = service.getBranchDaySchedule(request);
        return ResponseEntity.ok(result);
    }
}

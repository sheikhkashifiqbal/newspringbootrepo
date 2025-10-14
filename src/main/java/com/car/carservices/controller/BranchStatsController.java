package com.car.carservices.controller;

import com.car.carservices.dto.BranchBookingStatsDTO;
import com.car.carservices.service.BranchStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin // adjust if you have a central CORS config
public class BranchStatsController {

    private final BranchStatsService statsService;

    public BranchStatsController(BranchStatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * GET /api/stats/branches/booking
     * Optional: filter by companyId -> /api/stats/branches/booking?companyId=1
     *
     * JSON response shape (per item):
     * {
     *   "company_id": 1,
     *   "branch_id": 1,
     *   "service_ids": [1,2,3,4],
     *   "total_available_qty": 20,
     *   "total_reserve_service": 7,
     *   "percentage_booking": 35.0
     * }
     */
    @GetMapping("/branches/booking")
    public ResponseEntity<List<BranchBookingStatsDTO>> getBookingStats(
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        List<BranchBookingStatsDTO> result = statsService.getBranchBookingStats(companyId);
        return ResponseEntity.ok(result);
    }
}

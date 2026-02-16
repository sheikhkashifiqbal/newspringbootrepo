package com.car.carservices.controller;

import com.car.carservices.dto.SparepartsRatingRequest;
import com.car.carservices.dto.SparepartsRatingResponse;
import com.car.carservices.service.SparepartsRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class SparepartsRatingController {

    private final SparepartsRatingService sparepartsRatingService;

    public SparepartsRatingController(SparepartsRatingService sparepartsRatingService) {
        this.sparepartsRatingService = sparepartsRatingService;
    }

    @PostMapping("/spareparts-rating")
    public ResponseEntity<SparepartsRatingResponse> getSparepartsRatings(@RequestBody SparepartsRatingRequest request) {
        if (request == null || request.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sparepartsRatingService.getRatingsByBranchId(request.getBranchId()));
    }
}

// src/main/java/com/car/carservices/controller/SparePartOfferController.java
package com.car.carservices.controller;

import com.car.carservices.dto.*;
import com.car.carservices.service.SparePartOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/spare-parts/offers")
public class SparePartOfferController {

    private final SparePartOfferService service;

    public SparePartOfferController(SparePartOfferService service) {
        this.service = service;
    }

    // Existing endpoint (by user only)
    @PostMapping("/by-user")
    public ResponseEntity<List<SparePartOfferResponse>> byUser(@RequestBody UserIdRequest req) {
        if (req == null || req.getUserId() == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.byUserId(req.getUserId()));
    }

    // Existing NEW endpoint (by user AND branch)
    @PostMapping("/by-user-branch")
    public ResponseEntity<List<SparePartOfferResponse>> byUserAndBranch(@RequestBody UserBranchRequest req) {
        if (req == null || req.getUserId() == null || req.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.byUserAndBranch(req.getUserId(), req.getBranchId()));
    }

    // NEW endpoint (by branch only, with brand_id + brand_name)
    @PostMapping("/store-branch")
    public ResponseEntity<List<SparePartOfferBranchResponse>> byBranch(@RequestBody BranchIdRequest req) {
        if (req == null || req.branchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.byBranch(req.branchId()));
    }
}

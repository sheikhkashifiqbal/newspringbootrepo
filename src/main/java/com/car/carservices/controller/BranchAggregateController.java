package com.car.carservices.controller;

import com.car.carservices.dto.BranchAggregateResponse;
import com.car.carservices.service.BranchAggregateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches/services")

public class BranchAggregateController {

    private final BranchAggregateService service;

    public BranchAggregateController(BranchAggregateService service) {
        this.service = service;
    }

    // GET /api/branches
    @GetMapping
    public ResponseEntity<List<BranchAggregateResponse>> getAllBranches() {
        
        return ResponseEntity.ok(service.fetchAllBranchesWithServices());
    }
}

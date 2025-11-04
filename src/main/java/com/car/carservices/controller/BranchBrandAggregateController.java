package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandAggregateResponse;
import com.car.carservices.service.BranchBrandAggregateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches/brand-spareparts")
public class BranchBrandAggregateController {

    private final BranchBrandAggregateService service;

    public BranchBrandAggregateController(BranchBrandAggregateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BranchBrandAggregateResponse>> list() {
        return ResponseEntity.ok(service.getBranchesWithBrandsAndStars());
    }
}

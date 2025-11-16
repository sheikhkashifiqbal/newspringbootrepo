package com.car.carservices.controller;

import com.car.carservices.dto.PrSpBranchSparePartsRequest;
import com.car.carservices.dto.PrSpBranchSparePartsResponse;
import com.car.carservices.service.PrSpBranchSparePartsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prsp")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class PrSpBranchSparePartsController {

    private final PrSpBranchSparePartsService service;

    public PrSpBranchSparePartsController(PrSpBranchSparePartsService service) {
        this.service = service;
    }

    @PostMapping("/branch-spareparts")
    public ResponseEntity<List<PrSpBranchSparePartsResponse>> getSpareParts(
            @RequestBody PrSpBranchSparePartsRequest request) {
        if (request.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getBranchSpareParts(request));
    }
}

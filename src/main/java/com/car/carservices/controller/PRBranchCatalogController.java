package com.car.carservices.controller;

import com.car.carservices.dto.PRBranchCatalogRequest;
import com.car.carservices.dto.PRBranchCatalogResponse;
import com.car.carservices.service.PRBranchCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch-catalog")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRBranchCatalogController {

    private final PRBranchCatalogService service;

    public PRBranchCatalogController(PRBranchCatalogService service) {
        this.service = service;
    }

    @PostMapping("/by-branch")
    public ResponseEntity<PRBranchCatalogResponse> byBranch(@RequestBody PRBranchCatalogRequest req) {
        if (req == null || req.getBranch_id() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getCatalogForBranch(req.getBranch_id()));
    }
}

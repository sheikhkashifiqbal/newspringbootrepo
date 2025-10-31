package com.car.carservices.controller;

import com.car.carservices.dto.PRBranchBrandRequest;
import com.car.carservices.dto.PRBranchCatalogRequest;
import com.car.carservices.dto.PRBranchCatalogResponse;
import com.car.carservices.dto.PRBranchOnlyRequest;
import com.car.carservices.dto.PRBrandsOnlyResponse;
import com.car.carservices.dto.PRServicesOnlyResponse;
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
    @PostMapping("/brands")
public ResponseEntity<?> brands(@RequestBody PRBranchOnlyRequest req) {
    if (req == null || req.getBranch_id() == null) {
        return ResponseEntity.badRequest()
            .body(java.util.Map.of("message", "branch_id is required"));
    }
    var brands = service.getBrandsForBranch(req.getBranch_id());
    return ResponseEntity.ok(new PRBrandsOnlyResponse(brands));
}

    @PostMapping("/services")
    public ResponseEntity<?> services(@RequestBody PRBranchBrandRequest req) {
        if (req == null || req.getBranch_id() == null || req.getBrand_id() == null) {
            return ResponseEntity.badRequest()
                .body(java.util.Map.of("message", "branch_id and brand_id are required"));
        }
        var servicesList = service.getServicesForBranchAndBrand(req.getBranch_id(), req.getBrand_id());
        return ResponseEntity.ok(new PRServicesOnlyResponse(servicesList));
    }

}

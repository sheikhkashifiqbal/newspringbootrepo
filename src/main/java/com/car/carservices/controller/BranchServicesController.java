// src/main/java/com/yourapp/branchservices/web/BranchServicesController.java
package com.car.carservices.controller;

import com.car.carservices.dto.BranchServicesRequest;
import com.car.carservices.dto.BranchServicesResponse;
import com.car.carservices.service.BranchServicesQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// If you’re calling from Next.js on localhost, this helps during dev:
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branch-services")
public class BranchServicesController {

    private final BranchServicesQueryService service;

    public BranchServicesController(BranchServicesQueryService service) {
        this.service = service;
    }

    /**
     * Request body:
     * { "branch_id": 1 }
     *
     * Response:
     * [
     *   { "brand_name": "Toyoto", "available_services": ["service_name", ...] },
     *   { "brand_name": "Honda",  "available_services": ["service_name", ...] }
     * ]
     */
    //@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @PostMapping("/by-branch")
    public ResponseEntity<List<BranchServicesResponse>> listByBranch(@RequestBody BranchServicesRequest req) {
        if (req.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BranchServicesResponse> result = service.listBrandServicesForBranch(req.getBranchId());
        return ResponseEntity.ok(result);
    }
}

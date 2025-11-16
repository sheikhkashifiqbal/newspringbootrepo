// src/main/java/com/car/carservices/controller/BranchServicesController.java
package com.car.carservices.controller;

import com.car.carservices.dto.BranchServicesRequest;
import com.car.carservices.dto.BranchServicesResponse;
import com.car.carservices.service.BranchServicesQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branch-services")
public class BranchServicesController {

    private final BranchServicesQueryService service;

    public BranchServicesController(BranchServicesQueryService service) {
        this.service = service;
    }

    /**
     * JSON Request:
     * {
     *   "branch_id": 1,
     *   "brand_id": 1,   (optional)
     *   "service_id": 1  (optional)
     * }
     */
    @PostMapping("/by-branch")
    public ResponseEntity<List<BranchServicesResponse>> listByBranch(@RequestBody BranchServicesRequest req) {
        if (req.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }

        List<BranchServicesResponse> result = service.listBrandServicesForBranch(
                req.getBranchId(),
                req.getBrandId(),
                req.getServiceId()
        );
        return ResponseEntity.ok(result);
    }
}

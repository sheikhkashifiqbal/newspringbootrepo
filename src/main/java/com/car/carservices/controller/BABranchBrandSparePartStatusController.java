package com.car.carservices.controller;

import com.car.carservices.dto.BABranchBrandSparePartStatusRequestDTO;
import com.car.carservices.dto.BABranchBrandSparePartStatusResponseDTO;
import com.car.carservices.service.BABranchBrandSparePartStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ba/branch-brand-spare-part")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class BABranchBrandSparePartStatusController {

    private final BABranchBrandSparePartStatusService service;

    public BABranchBrandSparePartStatusController(BABranchBrandSparePartStatusService service) {
        this.service = service;
    }

    /**
     * PUT /api/ba/branch-brand-spare-part/status
     * Body:
     * { "branch_id": 1, "brand_id": 1, "status": "active" }
     */
    @PutMapping("/status")
    public ResponseEntity<BABranchBrandSparePartStatusResponseDTO> updateStatus(
            @RequestBody BABranchBrandSparePartStatusRequestDTO req
    ) {
        return ResponseEntity.ok(service.updateStatus(req));
    }
}
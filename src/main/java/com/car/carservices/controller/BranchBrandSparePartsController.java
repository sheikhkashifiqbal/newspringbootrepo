package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandSparePartsRequest;
import com.car.carservices.dto.SparePartsResponseDTO;
import com.car.carservices.service.BranchBrandSparePartsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch-catalog")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class BranchBrandSparePartsController {

    private final BranchBrandSparePartsService service;

    public BranchBrandSparePartsController(BranchBrandSparePartsService service) {
        this.service = service;
    }

    @PostMapping("/spareparts")
    public ResponseEntity<SparePartsResponseDTO> getSpareParts(@RequestBody BranchBrandSparePartsRequest request) {
        if (request.getBranchId() == null || request.getBrandId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getSparePartsByBranchAndBrand(request));
    }
}

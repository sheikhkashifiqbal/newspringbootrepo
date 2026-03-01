package com.car.carservices.controller;

import com.car.carservices.dto.SPBranchSparePartsRequestDTO;
import com.car.carservices.dto.SPBrandSparePartsResponseDTO;
import com.car.carservices.service.SPSparePartsByBranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spareparts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class SPSparePartsByBranchController {

    private final SPSparePartsByBranchService service;

    public SPSparePartsByBranchController(SPSparePartsByBranchService service) {
        this.service = service;
    }

    @PostMapping("/by-branch")
    public ResponseEntity<List<SPBrandSparePartsResponseDTO>> byBranch(
            @RequestBody SPBranchSparePartsRequestDTO req
    ) {
        return ResponseEntity.ok(service.getSparePartsByBranch(req.getBranchId()));
    }
}
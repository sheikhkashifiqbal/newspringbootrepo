package com.car.carservices.controller;

import com.car.carservices.dto.BrListRequestDTO;
import com.car.carservices.dto.BrListResponseDTO;
import com.car.carservices.service.BrListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrListController {

    private final BrListService service;

    public BrListController(BrListService service) {
        this.service = service;
    }

    @PostMapping("/SparePartBrandsList")
    public ResponseEntity<List<BrListResponseDTO>> SparePartBrandsList(@RequestBody BrListRequestDTO req) {
        if (req.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getBrandsList(req.getBranchId()));
    }
}

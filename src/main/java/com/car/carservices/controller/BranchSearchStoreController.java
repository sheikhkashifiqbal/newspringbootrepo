// com/car/carservices/controller/BranchSearchStoreController.java
package com.car.carservices.controller;

import com.car.carservices.dto.BranchSearchResultDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;
import com.car.carservices.service.BranchSearchStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches/services")
public class BranchSearchStoreController {

    private final BranchSearchStoreService service;
    public BranchSearchStoreController(BranchSearchStoreService service) { this.service = service; }

    @PostMapping("/search")
    public ResponseEntity<List<BranchSearchResultDTO>> search(@RequestBody BranchSearchServiceRequestDTO req) {
        return ResponseEntity.ok(service.searchBranches(req));
    }
}

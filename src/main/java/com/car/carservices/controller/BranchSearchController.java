package com.car.carservices.controller;

import com.car.carservices.dto.BranchSearchRequestDTO;
import com.car.carservices.dto.BranchSearchResponseDTO;
import com.car.carservices.service.BranchSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches/del")
public class BranchSearchController {

    @Autowired
    private BranchSearchService branchSearchService;

    @PostMapping("/search")
    public ResponseEntity<List<BranchSearchResponseDTO>> searchBranches(
        @RequestBody BranchSearchRequestDTO request
    ) {
        List<BranchSearchResponseDTO> results = branchSearchService.searchBranches(request);
        return ResponseEntity.ok(results);
    }
}

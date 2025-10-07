package com.car.carservices.controller;

import com.car.carservices.dto.BranchSearchDTO;
//import com.car.carservices.dto.BranchSearchResultDTO;
import com.car.carservices.dto.BrandGroupSearchResultDTO;
import com.car.carservices.service.BranchBrandSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/search")
public class BranchBrandSearchController {

    @Autowired
    private BranchBrandSearchService searchService;

@PostMapping("/branches")
public ResponseEntity<List<BrandGroupSearchResultDTO>> search(@RequestBody BranchSearchDTO dto) {
    return ResponseEntity.ok(searchService.searchBranches(dto));
}
}

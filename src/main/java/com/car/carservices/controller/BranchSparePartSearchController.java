package com.car.carservices.controller;

import com.car.carservices.dto.*;
import com.car.carservices.service.BranchSparePartSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/branches/spareparts")
public class BranchSparePartSearchController {

    @Autowired private BranchSparePartSearchService searchService;

    @PostMapping
    public ResponseEntity<List<BranchSparePartResultDTO>> search(@RequestBody BranchSparePartSearchDTO dto) {
        return ResponseEntity.ok(searchService.search(dto));
    }
}

package com.car.carservices.controller;

import com.car.carservices.dto.PRSparePartBranchItem;
import com.car.carservices.dto.PRSparePartSearchRequest;
import com.car.carservices.service.PRSparePartSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
@RequestMapping("/api/spare-parts")
public class PRSparePartSearchController {

    private final PRSparePartSearchService service;

    public PRSparePartSearchController(PRSparePartSearchService service) {
        this.service = service;
    }

    @PostMapping("/search-by-vin")
    public ResponseEntity<List<PRSparePartBranchItem>> searchByVin(@RequestBody PRSparePartSearchRequest req) {
        return ResponseEntity.ok(service.search(req)); // [] if nothing matches
    }
}

package com.car.carservices.controller;

import com.car.carservices.dto.BranchSearchRequest;
import com.car.carservices.dto.BranchSearchResponse;
import com.car.carservices.service.impl.BranchSearchForServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class BranchForSearchController {

    private final BranchSearchForServiceImpl service;

    public BranchForSearchController(BranchSearchForServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/search-branches")
    public List<BranchSearchResponse> searchBranches(@RequestBody BranchSearchRequest req) {
        return service.search(req);
    }
}

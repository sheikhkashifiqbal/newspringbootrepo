package com.car.carservices.controller;

import com.car.carservices.dto.SparePartsBranchRequest;
import com.car.carservices.dto.SparePartsBranchResponse;
import com.car.carservices.service.impl.SparePartsBranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spare-parts/offers")
@CrossOrigin
public class SparePartsBranchController {

    private final SparePartsBranchService service;

    public SparePartsBranchController(SparePartsBranchService service) {
        this.service = service;
    }

    @PostMapping("/by-branch")
    public List<SparePartsBranchResponse> getSparePartsByBranch(@RequestBody SparePartsBranchRequest req) {
        return service.getByBranch(req);
    }
}

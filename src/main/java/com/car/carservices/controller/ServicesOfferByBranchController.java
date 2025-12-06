package com.car.carservices.controller;

import com.car.carservices.dto.ServicesOfferByBranchRequest;
import com.car.carservices.dto.ServicesOfferByBranchResponse;
import com.car.carservices.service.ServicesOfferByBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServicesOfferByBranchController {

    private final ServicesOfferByBranchService servicesOfferByBranchService;

    public ServicesOfferByBranchController(ServicesOfferByBranchService servicesOfferByBranchService) {
        this.servicesOfferByBranchService = servicesOfferByBranchService;
    }

    @PostMapping("/services-offer-by-branch")
    public List<ServicesOfferByBranchResponse> getServicesOfferByBranch(
            @RequestBody ServicesOfferByBranchRequest request
    ) {
        if (request.getBranchId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "branch_id is required"
            );
        }

        return servicesOfferByBranchService.getServicesByBranchId(request.getBranchId());
    }
}

package com.car.carservices.service;

import com.car.carservices.dto.PrSpBranchSparePartsRequest;
import com.car.carservices.dto.PrSpBranchSparePartsResponse;
import com.car.carservices.repository.PrSpBranchSparePartsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrSpBranchSparePartsService {

    private final PrSpBranchSparePartsRepository repository;

    public PrSpBranchSparePartsService(PrSpBranchSparePartsRepository repository) {
        this.repository = repository;
    }

    public List<PrSpBranchSparePartsResponse> getBranchSpareParts(PrSpBranchSparePartsRequest request) {
        return repository.findSpareParts(request.getBranchId(), request.getBrandId());
    }
}

package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandSparePartsRequest;
import com.car.carservices.dto.SparePartsResponseDTO;
import com.car.carservices.repository.BranchAndBrandSparePartsRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchBrandSparePartsService {

    private final BranchAndBrandSparePartsRepository repo;

    public BranchBrandSparePartsService(BranchAndBrandSparePartsRepository repo) {
        this.repo = repo;
    }

    public SparePartsResponseDTO getSparePartsByBranchAndBrand(BranchBrandSparePartsRequest req) {
        var list = repo.findActiveSparePartsByBranchAndBrand(req.getBranchId(), req.getBrandId());
        return new SparePartsResponseDTO(list);
    }
}

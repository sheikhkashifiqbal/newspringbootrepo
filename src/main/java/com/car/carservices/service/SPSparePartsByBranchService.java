package com.car.carservices.service;

import com.car.carservices.dto.SPBrandSparePartsResponseDTO;

import java.util.List;

public interface SPSparePartsByBranchService {
    List<SPBrandSparePartsResponseDTO> getSparePartsByBranch(Long branchId);
}
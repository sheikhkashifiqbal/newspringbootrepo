package com.car.carservices.service;

import com.car.carservices.dto.BrListResponseDTO;

import java.util.List;

public interface BrListService {
    List<BrListResponseDTO> getBrandsList(Long branchId);
}

package com.car.carservices.service;

import com.car.carservices.dto.BranchSearchRequestDTO;
import com.car.carservices.dto.BranchSearchResponseDTO;
import java.util.List;

public interface BranchSearchService {
    List<BranchSearchResponseDTO> searchBranches(BranchSearchRequestDTO request);
}

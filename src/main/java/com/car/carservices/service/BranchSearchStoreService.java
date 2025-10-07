// com/car/carservices/service/BranchSearchStoreService.java
package com.car.carservices.service;

import com.car.carservices.dto.BranchSearchResultDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;

import java.util.List;

public interface BranchSearchStoreService {
    List<BranchSearchResultDTO> searchBranches(BranchSearchServiceRequestDTO request);
}

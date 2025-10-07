// com/car/carservices/service/BranchBrandSearchService.java
package com.car.carservices.service;

import com.car.carservices.dto.BrandGroupSearchResultDTO;
import com.car.carservices.dto.BranchSearchDTO;

import java.util.List;

public interface BranchBrandSearchService {
    List<BrandGroupSearchResultDTO> searchBranches(BranchSearchDTO searchDTO);
}

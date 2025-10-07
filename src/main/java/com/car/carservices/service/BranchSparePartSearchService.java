package com.car.carservices.service;

import com.car.carservices.dto.BranchSparePartResultDTO;
import com.car.carservices.dto.BranchSparePartSearchDTO;
import java.util.List;

public interface BranchSparePartSearchService {
    List<BranchSparePartResultDTO> search(BranchSparePartSearchDTO dto);
}
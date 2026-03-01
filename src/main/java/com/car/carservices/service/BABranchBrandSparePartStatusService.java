package com.car.carservices.service;

import com.car.carservices.dto.BABranchBrandSparePartStatusRequestDTO;
import com.car.carservices.dto.BABranchBrandSparePartStatusResponseDTO;

public interface BABranchBrandSparePartStatusService {
    BABranchBrandSparePartStatusResponseDTO updateStatus(BABranchBrandSparePartStatusRequestDTO req);
}
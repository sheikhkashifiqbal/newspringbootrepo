package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandServiceDTO;
import java.util.List;

public interface BranchBrandServiceService {
    BranchBrandServiceDTO create(BranchBrandServiceDTO dto);
    BranchBrandServiceDTO get(Long id);
    List<BranchBrandServiceDTO> getAll();
    BranchBrandServiceDTO update(Long id, BranchBrandServiceDTO dto);
    void delete(Long id);

    // NEW: bulk status update
    int updateStatusByBranchAndBrand(Long branchId, Long brandId, String status);

    // NEW: bulk status update by branch_id & service_id
int updateStatusByBranchAndService(Long branchId, Long serviceId, String status);

}

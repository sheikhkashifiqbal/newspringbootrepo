package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandServiceDTO;
import java.util.List;

public interface BranchBrandServiceService {
    BranchBrandServiceDTO create(BranchBrandServiceDTO dto);
    BranchBrandServiceDTO get(Long id);
    List<BranchBrandServiceDTO> getAll();
    BranchBrandServiceDTO update(Long id, BranchBrandServiceDTO dto);
    void delete(Long id);
}
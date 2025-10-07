package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandSparePartDTO;
import java.util.List;

public interface BranchBrandSparePartService {
    BranchBrandSparePartDTO create(BranchBrandSparePartDTO dto);
    BranchBrandSparePartDTO update(Long id, BranchBrandSparePartDTO dto);
    void delete(Long id);
    BranchBrandSparePartDTO getById(Long id);
    List<BranchBrandSparePartDTO> getAll();
}

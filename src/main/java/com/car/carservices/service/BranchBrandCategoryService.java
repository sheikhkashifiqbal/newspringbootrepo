package com.car.carservices.service;

import com.car.carservices.dto.BranchBrandCategoryDTO;

import java.util.List;

public interface BranchBrandCategoryService {
    BranchBrandCategoryDTO create(BranchBrandCategoryDTO dto);
    BranchBrandCategoryDTO get(Long id);
    List<BranchBrandCategoryDTO> getAll();
    BranchBrandCategoryDTO update(Long id, BranchBrandCategoryDTO dto);
    void delete(Long id);
}
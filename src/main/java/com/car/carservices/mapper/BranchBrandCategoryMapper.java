package com.car.carservices.mapper;

import com.car.carservices.dto.BranchBrandCategoryDTO;
import com.car.carservices.entity.BranchBrandCategory;
import org.springframework.stereotype.Component;

@Component
public class BranchBrandCategoryMapper {
    public BranchBrandCategoryDTO toDTO(BranchBrandCategory entity) {
        BranchBrandCategoryDTO dto = new BranchBrandCategoryDTO();
        dto.setId(entity.getId());
        dto.setBranchId(entity.getBranchId());
        dto.setBrandId(entity.getBrandId());
        dto.setCategoryId(entity.getCategoryId());
        return dto;
    }

    public BranchBrandCategory toEntity(BranchBrandCategoryDTO dto) {
        BranchBrandCategory entity = new BranchBrandCategory();
        entity.setId(dto.getId());
        entity.setBranchId(dto.getBranchId());
        entity.setBrandId(dto.getBrandId());
        entity.setCategoryId(dto.getCategoryId());
        return entity;
    }
}
package com.car.carservices.mapper;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.entity.*;
import org.springframework.stereotype.Component;

@Component
public class BranchBrandServiceMapper {

    public BranchBrandServiceDTO toDTO(BranchBrandService entity) {
        BranchBrandServiceDTO dto = new BranchBrandServiceDTO();
        dto.setId(entity.getId());
        dto.setBranchId(entity.getBranch().getBranchId());
        dto.setBrandId(entity.getBrand().getBrandId());
        dto.setServiceId(entity.getService().getServiceId());
        dto.setQty(entity.getQty());
        return dto;
    }

    public BranchBrandService toEntity(BranchBrandServiceDTO dto, Branch branch, Brand brand, ServiceEntity service) {
        BranchBrandService entity = new BranchBrandService();
        entity.setId(dto.getId());
        entity.setBranch(branch);
        entity.setBrand(brand);
        entity.setService(service);
        entity.setQty(dto.getQty());
        return entity;
    }
}
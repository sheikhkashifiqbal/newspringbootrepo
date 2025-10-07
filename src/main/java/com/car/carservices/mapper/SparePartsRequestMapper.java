// com/car/carservices/mapper/SparePartsRequestMapper.java
package com.car.carservices.mapper;

import com.car.carservices.dto.SparePartsRequestDTO;
import com.car.carservices.entity.SparePartsRequest;
import org.springframework.stereotype.Component;

@Component
public class SparePartsRequestMapper {

    public SparePartsRequestDTO toDTO(SparePartsRequest entity) {
        if (entity == null) return null;
        SparePartsRequestDTO dto = new SparePartsRequestDTO();
        dto.setSparepartsrequestId(entity.getSparepartsrequestId());
        dto.setUserId(entity.getUserId());
        dto.setSparepartsId(entity.getSparepartsId());
        dto.setBranchId(entity.getBranchId()); // NEW
        dto.setDate(entity.getDate());
        dto.setVinNumber(entity.getVinNumber());
        dto.setRequestStatus(entity.getRequestStatus());
        return dto;
    }

    public SparePartsRequest toEntity(SparePartsRequestDTO dto) {
        if (dto == null) return null;
        SparePartsRequest entity = new SparePartsRequest();
        entity.setSparepartsrequestId(dto.getSparepartsrequestId());
        entity.setUserId(dto.getUserId());
        entity.setSparepartsId(dto.getSparepartsId());
        entity.setBranchId(dto.getBranchId()); // NEW
        entity.setDate(dto.getDate());
        entity.setVinNumber(dto.getVinNumber());
        entity.setRequestStatus(dto.getRequestStatus());
        return entity;
    }
}

// SparePartNameMapper.java
package com.car.carservices.mapper;

import com.car.carservices.dto.SparePartNameDTO;
import com.car.carservices.entity.SparePartName;
import com.car.carservices.entity.SpareParts;
import org.springframework.stereotype.Component;

@Component
public class SparePartNameMapper {

    public SparePartName toEntity(SparePartNameDTO dto, SpareParts spareParts) {
        SparePartName entity = new SparePartName();
        entity.setId(dto.getId());
        entity.setSparePart(spareParts);
        entity.setSparepartsName(dto.getSparepartsName());
        return entity;
    }

    public SparePartNameDTO toDTO(SparePartName entity) {
        SparePartNameDTO dto = new SparePartNameDTO();
        dto.setId(entity.getId());
        dto.setSparepartsId(entity.getSparePart().getSparepartsId());
        dto.setSparepartsName(entity.getSparepartsName());
        return dto;
    }
}

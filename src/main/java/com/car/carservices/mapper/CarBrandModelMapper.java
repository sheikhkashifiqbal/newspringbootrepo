package com.car.carservices.mapper;

import com.car.carservices.dto.CarBrandModelDTO;
import com.car.carservices.entity.Brand;
import com.car.carservices.entity.CarBrandModel;
import org.springframework.stereotype.Component;

@Component
public class CarBrandModelMapper {
    public CarBrandModelDTO toDTO(CarBrandModel entity) {
        CarBrandModelDTO dto = new CarBrandModelDTO();
        dto.setId(entity.getId());
        dto.setBrandId(entity.getBrand().getBrandId());
        dto.setModelName(entity.getModelName());
        return dto;
    }

    public CarBrandModel toEntity(CarBrandModelDTO dto, Brand brand) {
        CarBrandModel entity = new CarBrandModel();
        entity.setId(dto.getId());
        entity.setBrand(brand);
        entity.setModelName(dto.getModelName());
        return entity;
    }
}

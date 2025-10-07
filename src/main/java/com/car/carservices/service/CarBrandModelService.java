package com.car.carservices.service;

import com.car.carservices.dto.CarBrandModelDTO;

import java.util.List;

public interface CarBrandModelService {
    CarBrandModelDTO create(CarBrandModelDTO dto);
    CarBrandModelDTO getById(Long id);
    List<CarBrandModelDTO> getAll();
    List<CarBrandModelDTO> getModelsByBrandId(Long brandId);
    CarBrandModelDTO update(Long id, CarBrandModelDTO dto);
    
    void delete(Long id);
}

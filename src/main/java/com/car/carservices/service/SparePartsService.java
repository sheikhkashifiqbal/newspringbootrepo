package com.car.carservices.service;

import com.car.carservices.dto.SparePartsDTO;
import java.util.List;

public interface SparePartsService {
    SparePartsDTO create(SparePartsDTO dto);
    SparePartsDTO get(Long id);
    List<SparePartsDTO> getAll();
    SparePartsDTO update(Long id, SparePartsDTO dto);
    void delete(Long id);
}
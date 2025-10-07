package com.car.carservices.service;

import com.car.carservices.dto.SparePartNameDTO;
import java.util.List;

public interface SparePartNameService {
    SparePartNameDTO create(SparePartNameDTO dto);
    SparePartNameDTO getById(Long id);
    List<SparePartNameDTO> getAll();
    SparePartNameDTO update(Long id, SparePartNameDTO dto);
    void delete(Long id);
}
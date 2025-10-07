// com/car/carservices/service/SparePartsRequestService.java
package com.car.carservices.service;

import com.car.carservices.dto.SparePartsRequestDTO;

import java.util.List;

public interface SparePartsRequestService {
    SparePartsRequestDTO create(SparePartsRequestDTO dto);
    SparePartsRequestDTO get(Long id);
    List<SparePartsRequestDTO> getAll();
    SparePartsRequestDTO update(Long id, SparePartsRequestDTO dto);
    void delete(Long id);
}

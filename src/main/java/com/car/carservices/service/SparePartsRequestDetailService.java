// src/main/java/com/car/carservices/service/SparePartsRequestDetailService.java
package com.car.carservices.service;

import com.car.carservices.dto.SparePartsRequestDetailDTO;

import java.util.List;

public interface SparePartsRequestDetailService {
    SparePartsRequestDetailDTO create(SparePartsRequestDetailDTO dto);
    SparePartsRequestDetailDTO get(Long id);
    List<SparePartsRequestDetailDTO> getAll();
    List<SparePartsRequestDetailDTO> getByRequestId(Long sparepartsrequestId);
    SparePartsRequestDetailDTO update(Long id, SparePartsRequestDetailDTO dto);
    void delete(Long id);
    long deleteByRequestId(Long sparepartsrequestId);
}

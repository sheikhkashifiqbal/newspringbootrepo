package com.car.carservices.service;

import com.car.carservices.dto.LmpsServiceRequestDTO;
import com.car.carservices.dto.LmpsServiceResponseDTO;

import java.util.List;

public interface LmpsServiceCrudService {

    LmpsServiceResponseDTO create(LmpsServiceRequestDTO dto);

    LmpsServiceResponseDTO getById(Long lmpsServiceId);

    List<LmpsServiceResponseDTO> getAll(Long serviceId, Long cityId);

    LmpsServiceResponseDTO update(Long lmpsServiceId, LmpsServiceRequestDTO dto);

    void delete(Long lmpsServiceId);
}

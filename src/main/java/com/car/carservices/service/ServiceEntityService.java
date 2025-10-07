package com.car.carservices.service;

import com.car.carservices.dto.ServiceEntityDTO;
import java.util.List;

public interface ServiceEntityService {
    ServiceEntityDTO create(ServiceEntityDTO dto);
    ServiceEntityDTO update(Long id, ServiceEntityDTO dto);
    void delete(Long id);
    ServiceEntityDTO getById(Long id);
    List<ServiceEntityDTO> getAll();
}
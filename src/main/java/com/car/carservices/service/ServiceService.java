package com.car.carservices.service;

import com.car.carservices.dto.ServiceDTO;
import java.util.List;

public interface ServiceService {
    ServiceDTO createService(ServiceDTO dto);
    ServiceDTO getService(Long id);
    List<ServiceDTO> getAllServices();
    ServiceDTO updateService(Long id, ServiceDTO dto);
    void deleteService(Long id);
}

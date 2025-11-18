package com.car.carservices.service.impl;

import com.car.carservices.dto.ServiceDTO;
import com.car.carservices.entity.ServiceEntity;
import com.car.carservices.repository.ServiceRepository;
import com.car.carservices.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository repository;

    @Override
    public ServiceDTO createService(ServiceDTO dto) {
        ServiceEntity entity = new ServiceEntity();
        entity.setServiceName(dto.getServiceName());
        entity.setServiceType(dto.getServiceType());
        entity = repository.save(entity);
        dto.setServiceId(entity.getServiceId());
        return dto;
    }

    @Override
    public ServiceDTO getService(Long id) {
        ServiceEntity entity = repository.findById(id).orElseThrow();
        ServiceDTO dto = new ServiceDTO();
        dto.setServiceId(entity.getServiceId());
        dto.setServiceName(entity.getServiceName());
        dto.setServiceType(entity.getServiceType());
        return dto;
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return repository.findAll().stream().map(entity -> {
            ServiceDTO dto = new ServiceDTO();
            dto.setServiceId(entity.getServiceId());
            dto.setServiceName(entity.getServiceName());
            dto.setServiceType(entity.getServiceType());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ServiceDTO updateService(Long id, ServiceDTO dto) {
        ServiceEntity entity = repository.findById(id).orElseThrow();
        entity.setServiceName(dto.getServiceName());
        entity.setServiceType(dto.getServiceName());
        entity = repository.save(entity);
        dto.setServiceId(entity.getServiceId());
        return dto;
    }

    @Override
    public void deleteService(Long id) {
        repository.deleteById(id);
    }
}

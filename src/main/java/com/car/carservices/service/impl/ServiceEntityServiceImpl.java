package com.car.carservices.service.impl;

import com.car.carservices.dto.ServiceEntityDTO;
import com.car.carservices.entity.ServiceEntity;
import com.car.carservices.mapper.ServiceEntityMapper;
import com.car.carservices.repository.ServiceEntityRepository;
import com.car.carservices.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {

    @Autowired
    private ServiceEntityRepository repository;

    @Autowired
    private ServiceEntityMapper mapper;

    @Override
    public ServiceEntityDTO create(ServiceEntityDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ServiceEntityDTO update(Long id, ServiceEntityDTO dto) {
        ServiceEntity entity = repository.findById(id).orElseThrow();
        entity.setServiceName(dto.getServiceName());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ServiceEntityDTO getById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElse(null);
    }

    @Override
    public List<ServiceEntityDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
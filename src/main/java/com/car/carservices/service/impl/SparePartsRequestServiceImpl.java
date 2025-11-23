// com/car/carservices/service/impl/SparePartsRequestServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsRequestDTO;
import com.car.carservices.entity.SparePartsRequest;
import com.car.carservices.mapper.SparePartsRequestMapper;
import com.car.carservices.repository.SparePartsRequestRepository;
import com.car.carservices.service.SparePartsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartsRequestServiceImpl implements SparePartsRequestService {

    @Autowired
    private SparePartsRequestRepository repository;

    @Autowired
    private SparePartsRequestMapper mapper;

    @Override
    public SparePartsRequestDTO create(SparePartsRequestDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public SparePartsRequestDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<SparePartsRequestDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SparePartsRequestDTO update(Long id, SparePartsRequestDTO dto) {
        SparePartsRequest entity = repository.findById(id).orElseThrow();

        // Only update fields if they are provided (not null)
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }

        if (dto.getSparepartsId() != null) {
            entity.setSparepartsId(dto.getSparepartsId());
        }

        if (dto.getBranchId() != null) {
            entity.setBranchId(dto.getBranchId());
        }

        if (dto.getDate() != null && !dto.getDate().isBlank()) {
            entity.setDate(dto.getDate());
        }

        if (dto.getVinNumber() != null && !dto.getVinNumber().isBlank()) {
            entity.setVinNumber(dto.getVinNumber());
        }

        if (dto.getRequestStatus() != null && !dto.getRequestStatus().isBlank()) {
            entity.setRequestStatus(dto.getRequestStatus());
        }

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

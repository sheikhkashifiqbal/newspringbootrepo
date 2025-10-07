package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsDTO;
import com.car.carservices.entity.SpareParts;
import com.car.carservices.repository.SparePartsRepository;
import com.car.carservices.service.SparePartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartsServiceImpl implements SparePartsService {

    @Autowired
    private SparePartsRepository repository;

    @Override
    public SparePartsDTO create(SparePartsDTO dto) {
        SpareParts entity = new SpareParts();
        entity.setSparepartsType(dto.getSparepartsType());
        entity.setCondition(dto.getCondition());
        entity = repository.save(entity);
        dto.setSparepartsId(entity.getSparepartsId());
        return dto;
    }

    @Override
    public SparePartsDTO get(Long id) {
        SpareParts entity = repository.findById(id).orElseThrow();
        SparePartsDTO dto = new SparePartsDTO();
        dto.setSparepartsId(entity.getSparepartsId());
        dto.setSparepartsType(entity.getSparepartsType());
        dto.setCondition(entity.getCondition());
        return dto;
    }

    @Override
    public List<SparePartsDTO> getAll() {
        return repository.findAll().stream().map(entity -> {
            SparePartsDTO dto = new SparePartsDTO();
            dto.setSparepartsId(entity.getSparepartsId());
            dto.setSparepartsType(entity.getSparepartsType());
            dto.setCondition(entity.getCondition());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public SparePartsDTO update(Long id, SparePartsDTO dto) {
        SpareParts entity = repository.findById(id).orElseThrow();
        entity.setSparepartsType(dto.getSparepartsType());
        entity.setCondition(dto.getCondition());
        entity = repository.save(entity);
        dto.setSparepartsId(entity.getSparepartsId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
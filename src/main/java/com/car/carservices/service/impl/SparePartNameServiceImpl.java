package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartNameDTO;
import com.car.carservices.entity.SparePartName;
import com.car.carservices.entity.SpareParts;
import com.car.carservices.mapper.SparePartNameMapper;
import com.car.carservices.repository.SparePartNameRepository;
import com.car.carservices.repository.SparePartsRepository;
import com.car.carservices.service.SparePartNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartNameServiceImpl implements SparePartNameService {

    @Autowired private SparePartNameRepository nameRepo;
    @Autowired private SparePartsRepository partsRepo;
    @Autowired private SparePartNameMapper mapper;

    @Override
    public SparePartNameDTO create(SparePartNameDTO dto) {
        SpareParts parent = partsRepo.findById(dto.getSparepartsId()).orElseThrow();
        SparePartName saved = nameRepo.save(mapper.toEntity(dto, parent));
        return mapper.toDTO(saved);
    }

    @Override
    public SparePartNameDTO getById(Long id) {
        return nameRepo.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    public List<SparePartNameDTO> getAll() {
        return nameRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SparePartNameDTO update(Long id, SparePartNameDTO dto) {
        SparePartName existing = nameRepo.findById(id).orElseThrow();
        SpareParts parent = partsRepo.findById(dto.getSparepartsId()).orElseThrow();

        existing.setSparePart(parent);
        existing.setSparepartsName(dto.getSparepartsName());

        return mapper.toDTO(nameRepo.save(existing));
    }

    @Override
    public void delete(Long id) {
        nameRepo.deleteById(id);
    }
}
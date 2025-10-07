package com.car.carservices.service.impl;

import com.car.carservices.dto.RateExperienceDTO;
import com.car.carservices.entity.RateExperience;
import com.car.carservices.mapper.RateExperienceMapper;
import com.car.carservices.repository.RateExperienceRepository;
import com.car.carservices.service.RateExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateExperienceServiceImpl implements RateExperienceService {

    @Autowired
    private RateExperienceRepository repository;

    @Autowired
    private RateExperienceMapper mapper;

    @Override
    public RateExperienceDTO create(RateExperienceDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public RateExperienceDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<RateExperienceDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RateExperienceDTO update(Long id, RateExperienceDTO dto) {
        RateExperience entity = repository.findById(id).orElseThrow();
        entity.setBranchBrandServiceID(dto.getBranchBrandServiceID());
        entity.setStars(dto.getStars());
        entity.setDescription(dto.getDescription());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
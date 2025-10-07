package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchBrandCategoryDTO;
import com.car.carservices.entity.BranchBrandCategory;
import com.car.carservices.mapper.BranchBrandCategoryMapper;
import com.car.carservices.repository.BranchBrandCategoryRepository;
import com.car.carservices.service.BranchBrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchBrandCategoryServiceImpl implements BranchBrandCategoryService {

    @Autowired
    private BranchBrandCategoryRepository repository;

    @Autowired
    private BranchBrandCategoryMapper mapper;

    @Override
    public BranchBrandCategoryDTO create(BranchBrandCategoryDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public BranchBrandCategoryDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<BranchBrandCategoryDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BranchBrandCategoryDTO update(Long id, BranchBrandCategoryDTO dto) {
        BranchBrandCategory entity = repository.findById(id).orElseThrow();
        entity.setBranchId(dto.getBranchId());
        entity.setBrandId(dto.getBrandId());
        entity.setCategoryId(dto.getCategoryId());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
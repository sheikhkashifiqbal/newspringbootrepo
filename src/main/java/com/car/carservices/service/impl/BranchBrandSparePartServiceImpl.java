package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchBrandSparePartDTO;
import com.car.carservices.entity.*;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchBrandSparePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchBrandSparePartServiceImpl implements BranchBrandSparePartService {

    @Autowired private BranchBrandSparePartRepository repository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private BrandRepository brandRepository;
    @Autowired private SparePartsRepository sparePartRepository;

    private BranchBrandSparePartDTO toDTO(BranchBrandSparePart entity) {
        BranchBrandSparePartDTO dto = new BranchBrandSparePartDTO();
        dto.setId(entity.getId());
        dto.setBranchId(entity.getBranch().getBranchId());
        dto.setBrandId(entity.getBrand().getBrandId());
        dto.setSparepartsId(entity.getSparePart().getSparepartsId());
        dto.setState(entity.getState());
        return dto;
    }

    @Override
    public BranchBrandSparePartDTO create(BranchBrandSparePartDTO dto) {
        BranchBrandSparePart entity = new BranchBrandSparePart();
        entity.setBranch(branchRepository.findById(dto.getBranchId()).orElseThrow());
        entity.setBrand(brandRepository.findById(dto.getBrandId()).orElseThrow());
        entity.setSparePart(sparePartRepository.findById(dto.getSparepartsId()).orElseThrow());
        entity.setState(dto.getState());
        return toDTO(repository.save(entity));
    }

    @Override
    public BranchBrandSparePartDTO update(Long id, BranchBrandSparePartDTO dto) {
        BranchBrandSparePart entity = repository.findById(id).orElseThrow();
        entity.setBranch(branchRepository.findById(dto.getBranchId()).orElseThrow());
        entity.setBrand(brandRepository.findById(dto.getBrandId()).orElseThrow());
        entity.setSparePart(sparePartRepository.findById(dto.getSparepartsId()).orElseThrow());
        entity.setState(dto.getState());
        return toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BranchBrandSparePartDTO getById(Long id) {
        return repository.findById(id).map(this::toDTO).orElseThrow();
    }

    @Override
    public List<BranchBrandSparePartDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
}
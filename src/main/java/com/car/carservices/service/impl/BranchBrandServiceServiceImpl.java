package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.entity.*;
import com.car.carservices.mapper.BranchBrandServiceMapper;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchBrandServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchBrandServiceServiceImpl implements BranchBrandServiceService {

    @Autowired
    private BranchBrandServiceRepository repository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BranchBrandServiceMapper mapper;

    @Override
    public BranchBrandServiceDTO create(BranchBrandServiceDTO dto) {
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow();
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        ServiceEntity service = serviceRepository.findById(dto.getServiceId()).orElseThrow();

        BranchBrandService entity = mapper.toEntity(dto, branch, brand, service);
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public BranchBrandServiceDTO get(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    public List<BranchBrandServiceDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BranchBrandServiceDTO update(Long id, BranchBrandServiceDTO dto) {
        BranchBrandService entity = repository.findById(id).orElseThrow();
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow();
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        ServiceEntity service = serviceRepository.findById(dto.getServiceId()).orElseThrow();

        entity.setBranch(branch);
        entity.setBrand(brand);
        entity.setService(service);
        entity.setQty(dto.getQty());

        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
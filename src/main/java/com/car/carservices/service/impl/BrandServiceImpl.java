package com.car.carservices.service.impl;

import com.car.carservices.dto.BrandDTO;
import com.car.carservices.entity.Brand;
import com.car.carservices.repository.BrandRepository;
import com.car.carservices.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public BrandDTO createBrand(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setBrandName(dto.getBrandName());
        brand = brandRepository.save(brand);
        dto.setBrandId(brand.getBrandId());
        return dto;
    }

    @Override
    public BrandDTO getBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        BrandDTO dto = new BrandDTO();
        dto.setBrandId(brand.getBrandId());
        dto.setBrandName(brand.getBrandName());
        return dto;
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream().map(brand -> {
            BrandDTO dto = new BrandDTO();
            dto.setBrandId(brand.getBrandId());
            dto.setBrandName(brand.getBrandName());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO dto) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        brand.setBrandName(dto.getBrandName());
        brand = brandRepository.save(brand);
        dto.setBrandId(brand.getBrandId());
        return dto;
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}

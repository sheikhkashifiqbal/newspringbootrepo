package com.car.carservices.service.impl;

import com.car.carservices.dto.CarBrandModelDTO;
import com.car.carservices.entity.Brand;
import com.car.carservices.entity.CarBrandModel;
import com.car.carservices.mapper.CarBrandModelMapper;
import com.car.carservices.repository.BrandRepository;
import com.car.carservices.repository.CarBrandModelRepository;
import com.car.carservices.service.CarBrandModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarBrandModelServiceImpl implements CarBrandModelService {

    @Autowired
    private CarBrandModelRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CarBrandModelMapper mapper;

    @Override
    public CarBrandModelDTO create(CarBrandModelDTO dto) {
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        return mapper.toDTO(repository.save(mapper.toEntity(dto, brand)));
    }

    @Override
    public CarBrandModelDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<CarBrandModelDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CarBrandModelDTO update(Long id, CarBrandModelDTO dto) {
        CarBrandModel existing = repository.findById(id).orElseThrow();
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        existing.setBrand(brand);
        existing.setModelName(dto.getModelName());
        return mapper.toDTO(repository.save(existing));
    }
    @Override
    public List<CarBrandModelDTO> getModelsByBrandId(Long brandId) {
    return repository.findByBrandBrandIdOrderByModelNameAsc(brandId)  // <- use sorted method
        .stream().map(this::toDTO).toList();
    }

    private CarBrandModelDTO toDTO(CarBrandModel model) {
        CarBrandModelDTO dto = new CarBrandModelDTO();
        dto.setId(model.getId());
        dto.setModelName(model.getModelName());
        dto.setBrandId(model.getBrand().getBrandId());
        return dto;
    }
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

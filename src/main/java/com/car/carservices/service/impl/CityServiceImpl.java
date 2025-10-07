package com.car.carservices.service.impl;

import com.car.carservices.dto.CityDTO;
import com.car.carservices.entity.City;
import com.car.carservices.mapper.CityMapper;
import com.car.carservices.repository.CityRepository;
import com.car.carservices.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityDTO createCity(CityDTO dto) {
        City saved = cityRepository.save(CityMapper.toEntity(dto));
        return CityMapper.toDTO(saved);
    }

    @Override
    public CityDTO updateCity(Long id, CityDTO dto) {
        City existing = cityRepository.findById(id).orElseThrow();
        existing.setCity(dto.getCity());
        return CityMapper.toDTO(cityRepository.save(existing));
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public CityDTO getCityById(Long id) {
        return cityRepository.findById(id).map(CityMapper::toDTO).orElseThrow();
    }

    @Override
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream().map(CityMapper::toDTO).collect(Collectors.toList());
    }
}

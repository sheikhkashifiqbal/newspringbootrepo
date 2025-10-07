package com.car.carservices.service;

import com.car.carservices.dto.CarDetailsDTO;
import com.car.carservices.entity.Brand;
//import com.car.carservices.dto.CompanyDTO;
import com.car.carservices.entity.CarDetails;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.mapper.CarDetailsMapper;
import com.car.carservices.repository.BrandRepository;
import com.car.carservices.repository.CarDetailsRepository;
import com.car.carservices.repository.UserRegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarDetailsService {
    @Autowired private CarDetailsRepository carRepo;
    @Autowired private UserRegistrationRepository userRepo;
    @Autowired private CarDetailsMapper mapper;
        
    @Autowired private BrandRepository brandRepository;


    public List<CarDetailsDTO> getAll() {
        
        return carRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }


    public CarDetailsDTO create(CarDetailsDTO dto) {
        UserRegistration user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow(); 
        CarDetails car = mapper.toEntity(dto, user, brand);
        return mapper.toDTO(carRepo.save(car));
    }

    public List<CarDetailsDTO> getByUserId(Long userId) {
        return carRepo.findByUserId(userId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public Optional<CarDetailsDTO> getById(Long id) {
        return carRepo.findById(id).map(mapper::toDTO);
    }

    public CarDetailsDTO update(Long id, CarDetailsDTO dto) {
        CarDetails existing = carRepo.findById(id).orElseThrow();
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        //existing.setCarBrand(dto.getCarBrand());
        existing.setBrand(brand);
        existing.setCarModel(dto.getCarModel());
        existing.setVinNumber(dto.getVinNumber());
        existing.setPlateNumber(dto.getPlateNumber());
        return mapper.toDTO(carRepo.save(existing));
    }

    public void delete(Long id) {
        carRepo.deleteById(id);
    }
}

package com.car.carservices.mapper;

import com.car.carservices.dto.CarDetailsDTO;
import com.car.carservices.entity.CarDetails;
import com.car.carservices.entity.UserRegistration;
import org.springframework.stereotype.Component;
import com.car.carservices.entity.Brand;

@Component
public class CarDetailsMapper {
    public CarDetailsDTO toDTO(CarDetails car) {
        CarDetailsDTO dto = new CarDetailsDTO();
        dto.setCarId(car.getCarId());
        dto.setCarModel(car.getCarModel());
        dto.setVinNumber(car.getVinNumber());
        dto.setPlateNumber(car.getPlateNumber());

        if (car.getBrand() != null) {
          dto.setBrandId(car.getBrand().getBrandId());
       
    }

    if (car.getUser() != null) {
       dto.setUserId(car.getUser().getId());
       
    }

        return dto;
    }

    public CarDetails toEntity(CarDetailsDTO dto, UserRegistration user, Brand brand) {
        CarDetails car = new CarDetails();
        car.setUser(user);
       // car.setCarBrand(dto.getCarBrand());
        car.setBrand(brand);
        car.setCarModel(dto.getCarModel());
        car.setVinNumber(dto.getVinNumber());
        car.setPlateNumber(dto.getPlateNumber());
        return car;
    }
}

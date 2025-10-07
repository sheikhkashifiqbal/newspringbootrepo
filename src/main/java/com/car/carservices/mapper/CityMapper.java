package com.car.carservices.mapper;

import com.car.carservices.dto.CityDTO;
import com.car.carservices.entity.City;

public class CityMapper {
    public static CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setId(city.getId());
        dto.setCity(city.getCity());
        return dto;
    }

    public static City toEntity(CityDTO dto) {
        City city = new City();
        city.setId(dto.getId());
        city.setCity(dto.getCity());
        return city;
    }
}

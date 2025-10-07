package com.car.carservices.service;

import com.car.carservices.dto.CityDTO;
import java.util.List;

public interface CityService {
    CityDTO createCity(CityDTO dto);
    CityDTO updateCity(Long id, CityDTO dto);
    void deleteCity(Long id);
    CityDTO getCityById(Long id);
    List<CityDTO> getAllCities();
}

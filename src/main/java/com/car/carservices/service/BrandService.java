package com.car.carservices.service;

import com.car.carservices.dto.BrandDTO;
import java.util.List;

public interface BrandService {
    BrandDTO createBrand(BrandDTO brandDTO);
    BrandDTO getBrand(Long id);
    List<BrandDTO> getAllBrands();
    BrandDTO updateBrand(Long id, BrandDTO brandDTO);
    void deleteBrand(Long id);
}

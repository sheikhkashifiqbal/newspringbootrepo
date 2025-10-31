package com.car.carservices.dto;

import java.util.List;

public class PRBrandsOnlyResponse {
    private List<PRBrandDTO> brands;

    public PRBrandsOnlyResponse(List<PRBrandDTO> brands) {
        this.brands = brands;
    }

    public List<PRBrandDTO> getBrands() { return brands; }
}

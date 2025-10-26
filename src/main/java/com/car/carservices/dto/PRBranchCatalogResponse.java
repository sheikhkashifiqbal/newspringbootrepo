package com.car.carservices.dto;

import java.util.List;

public class PRBranchCatalogResponse {
    private List<PRBrandDTO> brands;
    private List<PRServiceDTO> services;
    private List<PRModelDTO> models;

    public PRBranchCatalogResponse(List<PRBrandDTO> brands,
                                   List<PRServiceDTO> services,
                                   List<PRModelDTO> models) {
        this.brands = brands;
        this.services = services;
        this.models = models;
    }

    public List<PRBrandDTO> getBrands() { return brands; }
    public List<PRServiceDTO> getServices() { return services; }
    public List<PRModelDTO> getModels() { return models; }
}

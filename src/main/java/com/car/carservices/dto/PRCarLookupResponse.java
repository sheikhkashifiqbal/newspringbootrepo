package com.car.carservices.dto;

public class PRCarLookupResponse {
    private PRBrandDTO brand; // nullable if not found
    private PRModelDTO model; // nullable if not found

    public PRCarLookupResponse() {}
    public PRCarLookupResponse(PRBrandDTO brand, PRModelDTO model) {
        this.brand = brand;
        this.model = model;
    }

    public PRBrandDTO getBrand() { return brand; }
    public void setBrand(PRBrandDTO brand) { this.brand = brand; }
    public PRModelDTO getModel() { return model; }
    public void setModel(PRModelDTO model) { this.model = model; }
}

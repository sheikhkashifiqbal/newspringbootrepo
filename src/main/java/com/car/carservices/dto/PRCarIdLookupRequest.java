package com.car.carservices.dto;

public class PRCarIdLookupRequest {
    private Long user_id;
    private Long brand_id;
    private String model_brand;

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public Long getBrand_id() { return brand_id; }
    public void setBrand_id(Long brand_id) { this.brand_id = brand_id; }

    public String getModel_brand() { return model_brand; }
    public void setModel_brand(String model_brand) { this.model_brand = model_brand; }
}

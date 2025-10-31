package com.car.carservices.dto;

public class PRCarIdLookupResponse {
    private Long car_id;

    public PRCarIdLookupResponse(Long car_id) {
        this.car_id = car_id;
    }

    public Long getCar_id() { return car_id; }
}

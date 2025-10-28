package com.car.carservices.dto;

import java.util.List;

public class PRUserPlatesResponse {
    private List<String> plate_number;

    public PRUserPlatesResponse(List<String> plate_number) {
        this.plate_number = plate_number;
    }

    public List<String> getPlate_number() { return plate_number; }
}

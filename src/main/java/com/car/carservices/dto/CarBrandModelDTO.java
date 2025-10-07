package com.car.carservices.dto;

import lombok.Data;

@Data
public class CarBrandModelDTO {
    private Long id;
    private Long brandId;
    private String modelName;
}

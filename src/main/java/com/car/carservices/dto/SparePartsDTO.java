package com.car.carservices.dto;

import lombok.Data;

@Data
public class SparePartsDTO {
    private Long sparepartsId;
    private String sparepartsType;
    private String condition;
}
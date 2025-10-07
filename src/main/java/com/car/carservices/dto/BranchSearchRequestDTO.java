package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchSearchRequestDTO {
    private String brandName;
    private String carModel; // Optional, if models are implemented
    private String serviceName;
    //private String workdays;
    private String location;
}
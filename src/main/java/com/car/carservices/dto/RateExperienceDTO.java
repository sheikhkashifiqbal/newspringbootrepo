package com.car.carservices.dto;

import lombok.Data;

@Data
public class RateExperienceDTO {
    private Long rateExperienceID;
    private Long branchBrandServiceID;
    private int stars;
    private String description;
}
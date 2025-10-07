package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RateExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateExperienceID;

    private Long branchBrandServiceID;
    private int stars;
    private String description;
}
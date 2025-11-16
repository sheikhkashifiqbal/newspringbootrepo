package com.car.carservices.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RateExperienceDTO {
    private Long rateExperienceID;
    private Long branchBrandServiceID; // ✅ correct field
    private Long userId;
    private String description;
    private Integer stars;
    private LocalDate date;
}

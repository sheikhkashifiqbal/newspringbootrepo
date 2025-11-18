// dto/BranchSearchResponse.java
package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchSearchResponse {

    private Long branchId;
    private String branchName;
    private String location;
    private Double companyRating;
    private Double distanceKm;
    private String companyLogoUrl;
    private List<String> availableTimeSlots;
}

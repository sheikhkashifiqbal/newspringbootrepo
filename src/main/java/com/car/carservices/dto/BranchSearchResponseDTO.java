package com.car.carservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchSearchResponseDTO {
    private String companyName;
    private String branchName;
    private String serviceName;
    private String brandName;
    private String location;
   // private String workdays;
   // private String workingHours;
}
package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchWithServicesDTO {
    private Long branchId;
    private String branchName;
    private String location;
   // private String workdays;
   // private String workingHours;
    private List<String> services;
}

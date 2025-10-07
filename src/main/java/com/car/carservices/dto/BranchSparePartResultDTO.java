package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchSparePartResultDTO {
    private Long branchId;
    private String branchName;
    private String branchAddress;
    private String brandName;   // brand derived from VIN → car_details → brand
    private String location;    // city
    private int totalMatchedParts; // how many of the requested names does this branch stock?
}
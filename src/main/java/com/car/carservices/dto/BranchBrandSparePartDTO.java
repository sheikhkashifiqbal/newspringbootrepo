package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchBrandSparePartDTO {
    private Long id;
    private Long branchId;
    private Long brandId;
    private Long sparepartsId;
    private String state;
}
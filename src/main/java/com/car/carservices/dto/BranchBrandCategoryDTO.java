package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchBrandCategoryDTO {
    private Long id;
    private Long branchId;
    private Long brandId;
    private Long categoryId;
}
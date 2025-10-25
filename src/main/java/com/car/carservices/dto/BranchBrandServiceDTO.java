package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchBrandServiceDTO {
    private Long id;
    private Long branchId;
    private Long brandId;
    private Long serviceId;
    private Integer qty;

    // NEW: status of the mapping ("active" | "inactive")
    private String status;
}

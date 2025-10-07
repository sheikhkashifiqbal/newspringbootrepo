package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchSearchDTO {
    private Long brandId;
    private List<Long> serviceIds;
}

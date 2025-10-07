package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompanyBranchesRequest {
    @JsonProperty("company_id")
    private Long companyId;
}

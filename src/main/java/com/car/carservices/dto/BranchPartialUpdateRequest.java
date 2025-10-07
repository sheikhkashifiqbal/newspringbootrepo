package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // omit nulls from JSON
public class BranchPartialUpdateRequest {

    // Scalars (send only the ones you want to change)
    private String branchName;
    private String branchCode;

    private String branchManagerName;
    private String branchManagerSurname;

    private String branchAddress; // legacy name in your API
    private String address;       // your new "address" field (keep both for compatibility)

    private String city;          // your new "city" field
    private String location;

    private String loginEmail;
    private String password;

    private String logoImg;
    private String branchCoverImg;

    private String status;

    private Double latitude;
    private Double longitude;

    // If you want to reassign branch to another company
    @JsonProperty("company_id")
    private Long companyId;
}
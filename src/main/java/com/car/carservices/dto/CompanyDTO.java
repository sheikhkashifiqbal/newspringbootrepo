package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompanyDTO {
    private Long companyId;
    private String companyName;
    private String brandName;
    private String taxId;
    private String managerName;
    private String managerSurname;
    private String managerPhone;
    private String managerMobile;
    private String managerEmail;
    private String website;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String tinPhoto;
}

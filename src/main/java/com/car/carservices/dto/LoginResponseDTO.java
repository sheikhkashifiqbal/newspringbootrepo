// com/car/carservices/dto/LoginResponseDTO.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private String token;

    // which role actually authenticated: "user" | "company_manager" | "branch_manager"
    private String role;

    // user
    private Long id;
    private String fullName;
    private String email;

    // company manager
    private Long companyId;
    private String companyName;

    // branch manager
    private Long branchId;
    private String branchName;
}

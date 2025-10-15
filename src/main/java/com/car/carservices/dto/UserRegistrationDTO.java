package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegistrationDTO {
    private Long id;
    private String fullName;
    private LocalDate birthday;
    private String gender;
    private String email;
    private String mobile;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}

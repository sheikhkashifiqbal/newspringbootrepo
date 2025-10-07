package com.car.carservices.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}

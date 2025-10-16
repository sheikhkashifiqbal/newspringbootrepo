// src/main/java/com/car/carservices/dto/PRResetPasswordRequest.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class PRResetPasswordRequest {
    private String email;    // optional
    private String mobile;   // optional (E.164 like +923301626155)
    private String password; // required (raw, will be BCrypt-encoded)
    private String type;     // required: "user" | "manager"
}

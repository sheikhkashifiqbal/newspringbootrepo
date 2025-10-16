// src/main/java/com/car/carservices/dto/PRResetPasswordResponse.java
package com.car.carservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PRResetPasswordResponse {
    private boolean success;    // true if changed
    private String message;     // "Password is modified successfully" | "Password is not changed" | error
    private String matchedBy;   // "email" | "mobile" | null
    private String matchedValue;// the email or mobile used to match
}

// com/car/carservices/dto/PRVerifyContactRequest.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class PRVerifyContactRequest {
    private String mobile;  // e.g. "+971501711957"
    private String email;   // e.g. "test@example.com"
    private String type;    // "user" | "manager"
}

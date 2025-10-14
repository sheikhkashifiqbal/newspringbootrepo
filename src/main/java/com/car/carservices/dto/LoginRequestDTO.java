// com/car/carservices/dto/LoginRequestDTO.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;   // raw password (we will BCrypt-match against stored hash)
    private String type;       // "user" | "company_manager" | "branch_manager"
}

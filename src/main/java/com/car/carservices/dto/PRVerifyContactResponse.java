// com/car/carservices/dto/PRVerifyContactResponse.java
package com.car.carservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PRVerifyContactResponse {
    private boolean result; // true if matched per the rules, else false
}

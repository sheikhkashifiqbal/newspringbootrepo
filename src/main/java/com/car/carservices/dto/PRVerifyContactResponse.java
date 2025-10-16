// com/car/carservices/dto/PRVerifyContactResponse.java
package com.car.carservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PRVerifyContactResponse {
    private boolean result;          // true if matched, else false
    private String matchedType;      // "email", "mobile", or null
    private String matchedValue;     // the email or mobile that matched (echoed back)
    private String code;             // 4-digit code if matched; null otherwise
}

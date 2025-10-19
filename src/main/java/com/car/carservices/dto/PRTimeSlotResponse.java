// src/main/java/com/car/carservices/dto/PRTimeSlotResponse.java
package com.car.carservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PRTimeSlotResponse {
    private List<String> availableTimeSlots; // e.g., ["09:00","09:30",...]
    private String message;                  // "", or "No service available"
}

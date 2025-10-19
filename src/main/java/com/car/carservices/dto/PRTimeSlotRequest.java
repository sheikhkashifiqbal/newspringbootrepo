// src/main/java/com/car/carservices/dto/PRTimeSlotRequest.java
package com.car.carservices.dto;

import lombok.Data;

@Data
public class PRTimeSlotRequest {
    private Long branch_id;   // required
    private Long brand_id;    // optional
    private Long model_id;    // optional (not used in capacity by default)
    private Long service_id;  // optional
    private String date;      // required, ISO "yyyy-MM-dd"
}

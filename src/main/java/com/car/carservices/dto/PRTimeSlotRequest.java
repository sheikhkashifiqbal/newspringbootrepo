// src/main/java/com/car/carservices/dto/PRTimeSlotRequest.java
package com.car.carservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor          // <--- ensures new PRTimeSlotRequest() compiles
@AllArgsConstructor         // <--- optional: lets you use the full-args constructor if you want
public class PRTimeSlotRequest {
    private Long branch_id;   // required
    private Long brand_id;    // optional
    private Long model_id;    // optional
    private Long service_id;  // optional
    private String date;      // required, ISO "yyyy-MM-dd"
}

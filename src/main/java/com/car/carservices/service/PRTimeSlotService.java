// src/main/java/com/car/carservices/service/PRTimeSlotService.java
package com.car.carservices.service;

import com.car.carservices.dto.PRTimeSlotRequest;
import com.car.carservices.dto.PRTimeSlotResponse;

public interface PRTimeSlotService {
    PRTimeSlotResponse getAvailableSlots(PRTimeSlotRequest req);
}

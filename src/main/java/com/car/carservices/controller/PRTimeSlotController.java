// src/main/java/com/car/carservices/controller/PRTimeSlotController.java
package com.car.carservices.controller;

import com.car.carservices.dto.PRTimeSlotRequest;
import com.car.carservices.dto.PRTimeSlotResponse;
import com.car.carservices.service.PRTimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRTimeSlotController {

    private final PRTimeSlotService service;

    public PRTimeSlotController(PRTimeSlotService service) {
        this.service = service;
    }

    @PostMapping("/available-slots")
    public ResponseEntity<PRTimeSlotResponse> available(@RequestBody PRTimeSlotRequest req) {
        return ResponseEntity.ok(service.getAvailableSlots(req));
    }
}

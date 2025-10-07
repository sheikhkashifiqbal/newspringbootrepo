// src/main/java/com/car/carservices/api/ReservationQueryController.java
package com.car.carservices.controller;

import com.car.carservices.dto.UserIddRequest;
import com.car.carservices.dto.ReservationSummaryResponse;
import com.car.carservices.service.ReservationQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/reservations")
public class ReservationQueryController {

    private static final Logger log = LoggerFactory.getLogger(ReservationQueryController.class);

    private final ReservationQueryService service;
    private final ObjectMapper objectMapper;  // Spring Boot auto-configures this bean

    public ReservationQueryController(ReservationQueryService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    // POST http://localhost:8081/api/reservations/by-user
   @PostMapping("/by-user")
    public ResponseEntity<List<ReservationSummaryResponse>> byUser(@RequestBody UserIddRequest req) {
        try {
            // 🔎 Print the JSON POST request
            log.info("POST /api/reservations/by-user body: {}", objectMapper.writeValueAsString(req));
        } catch (Exception e) {
            log.warn("Could not serialize request body", e);
        }

        if (req == null || req.getUserId() == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.byUserId(req.getUserId()));
    }
}

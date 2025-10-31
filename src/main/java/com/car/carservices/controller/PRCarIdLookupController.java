package com.car.carservices.controller;

import com.car.carservices.dto.PRCarIdLookupRequest;
import com.car.carservices.dto.PRCarIdLookupResponse;
import com.car.carservices.service.PRCarIdLookupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRCarIdLookupController {

    private final PRCarIdLookupService service;

    public PRCarIdLookupController(PRCarIdLookupService service) {
        this.service = service;
    }

    @PostMapping("/id-by-user-brand-model")
    public ResponseEntity<?> idByUserBrandModel(@RequestBody PRCarIdLookupRequest req) {
        if (req == null || req.getUser_id() == null || req.getBrand_id() == null
                || req.getModel_brand() == null || req.getModel_brand().isBlank()) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("message", "user_id, brand_id, and model_brand are required"));
        }

        return service.findCarId(req.getUser_id(), req.getBrand_id(), req.getModel_brand())
                .<ResponseEntity<?>>map(id -> ResponseEntity.ok(new PRCarIdLookupResponse(id)))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(java.util.Map.of("message", "No car found for given parameters")));
    }
}

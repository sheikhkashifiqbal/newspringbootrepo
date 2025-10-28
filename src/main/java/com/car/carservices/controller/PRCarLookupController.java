package com.car.carservices.controller;

import com.car.carservices.dto.PRCarLookupRequest;
import com.car.carservices.dto.PRCarLookupResponse;
import com.car.carservices.service.PRCarLookupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRCarLookupController {

    private final PRCarLookupService service;

    public PRCarLookupController(PRCarLookupService service) {
        this.service = service;
    }

    @PostMapping("/brand-model-by-plate")
    public ResponseEntity<?> brandModelByPlate(@RequestBody PRCarLookupRequest req) {
        if (req == null || req.getPlate_number() == null || req.getPlate_number().isBlank()) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of("message", "plate_number is required")
            );
        }

        return service.findByPlate(req.getPlate_number())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(java.util.Map.of("message", "No car/brand/model found for the given plate_number")));
    }
}

package com.car.carservices.controller;

import com.car.carservices.dto.PRUserPlatesRequest;
import com.car.carservices.dto.PRUserPlatesResponse;
import com.car.carservices.service.PRUserPlatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRUserPlatesController {

    private final PRUserPlatesService service;

    public PRUserPlatesController(PRUserPlatesService service) {
        this.service = service;
    }

    @PostMapping("/plates-by-user")
    public ResponseEntity<?> platesByUser(@RequestBody PRUserPlatesRequest req) {
        if (req == null || req.getUser_id() == null) {
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of("message", "user_id is required"));
        }
        return ResponseEntity.ok(service.listPlatesForUser(req.getUser_id()));
    }
}

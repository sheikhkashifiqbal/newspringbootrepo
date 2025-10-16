// src/main/java/com/car/carservices/controller/PRPasswordResetController.java
package com.car.carservices.controller;

import com.car.carservices.dto.PRResetPasswordRequest;
import com.car.carservices.dto.PRResetPasswordResponse;
import com.car.carservices.service.PRPasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRPasswordResetController {

    private final PRPasswordResetService service;

    public PRPasswordResetController(PRPasswordResetService service) {
        this.service = service;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<PRResetPasswordResponse> reset(@RequestBody PRResetPasswordRequest req) {
        // Backend returns message; your frontend can show a toast with it.
        PRResetPasswordResponse res = service.reset(req);
        return ResponseEntity.ok(res);
    }
}

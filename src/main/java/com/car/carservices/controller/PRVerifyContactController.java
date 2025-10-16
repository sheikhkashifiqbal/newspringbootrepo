// com/car/carservices/controller/PRVerifyContactController.java
package com.car.carservices.controller;

import com.car.carservices.dto.PRVerifyContactRequest;
import com.car.carservices.dto.PRVerifyContactResponse;
import com.car.carservices.service.PRVerifyContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
public class PRVerifyContactController {

    private final PRVerifyContactService service;

    public PRVerifyContactController(PRVerifyContactService service) {
        this.service = service;
    }

    @PostMapping("/contact")
    public ResponseEntity<PRVerifyContactResponse> verify(@RequestBody PRVerifyContactRequest req) {
        // Optional: validate type value here if you want a 400 on unexpected types
        return ResponseEntity.ok(service.verify(req));
    }
}

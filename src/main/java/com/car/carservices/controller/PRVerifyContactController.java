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
    public ResponseEntity<?> verify(@RequestBody PRVerifyContactRequest req) {
        String type = req.getType() == null ? "" : req.getType().trim().toLowerCase();
        if (!type.equals("user") && !type.equals("manager")) {
            return ResponseEntity.badRequest().body("type must be 'user' or 'manager'");
        }
        boolean matched = service.verify(req);
        return ResponseEntity.ok(new PRVerifyContactResponse(matched));
    }
}

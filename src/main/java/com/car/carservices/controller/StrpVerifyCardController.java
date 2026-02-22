package com.car.carservices.controller;

import com.car.carservices.dto.StrpVerifyCardRequest;
import com.car.carservices.dto.StrpVerifyCardResponse;
import com.car.carservices.service.StrpVerifyCardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*") // adjust as needed
public class StrpVerifyCardController {

    private final StrpVerifyCardService verifyCardService;

    public StrpVerifyCardController(StrpVerifyCardService verifyCardService) {
        this.verifyCardService = verifyCardService;
    }

    @PostMapping("/verify-card")
    public ResponseEntity<StrpVerifyCardResponse> verifyCard(@Valid @RequestBody StrpVerifyCardRequest request) {
        StrpVerifyCardResponse response = verifyCardService.verifyCard(request);
        if (!response.isVerified()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}

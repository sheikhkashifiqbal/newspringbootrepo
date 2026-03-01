package com.car.carservices.controller;

import com.car.carservices.dto.CreatePaymentIntentRequest;
import com.car.carservices.dto.CreatePaymentIntentResponse;
import com.car.carservices.service.StripePaymentIntentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*") // adjust for your frontend domain
public class StripePaymentIntentController {

    private final StripePaymentIntentService service;

    public StripePaymentIntentController(StripePaymentIntentService service) {
        this.service = service;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> create(@Valid @RequestBody CreatePaymentIntentRequest req) {
        try {
            CreatePaymentIntentResponse resp = service.createPaymentIntent(req);
            return ResponseEntity.ok(resp);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Stripe error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Server error: " + e.getMessage()));
        }
    }

    static class ErrorResponse {
        private String message;
        public ErrorResponse() {}
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
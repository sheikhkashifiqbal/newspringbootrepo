package com.car.carservices.controller;

import com.car.carservices.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
@RequestMapping("/api/users/check")
public class UserEmailCheckController {

    private final UserRegistrationService service;

    public UserEmailCheckController(UserRegistrationService service) {
        this.service = service;
    }

    // GET: /api/users/check/email?email=foo@bar.com
    @GetMapping("/email")
    public ResponseEntity<Map<String, Object>> checkEmailGet(@RequestParam("email") String email) {
        boolean unique = service.isEmailUnique(email);
        Map<String, Object> body = new HashMap<>();
        body.put("field", "email");
        body.put("value", email);
        body.put("isUnique", unique);
        body.put("message", unique ? "" : "Duplicate email");
        return ResponseEntity.ok(body);
    }

    // POST: /api/users/check/email   body: {"email":"foo@bar.com"}
    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> checkEmailPost(@RequestBody EmailCheckRequest req) {
        boolean unique = service.isEmailUnique(req.getEmail());
        Map<String, Object> body = new HashMap<>();
        body.put("field", "email");
        body.put("value", req.getEmail());
        body.put("isUnique", unique);
        body.put("message", unique ? "" : "Duplicate email");
        return ResponseEntity.ok(body);
    }

    // Simple POJO for POST body
    public static class EmailCheckRequest {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}

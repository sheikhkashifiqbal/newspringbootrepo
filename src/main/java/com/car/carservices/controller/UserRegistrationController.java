package com.car.carservices.controller;

import com.car.carservices.dto.UserRegistrationDTO;
import com.car.carservices.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService service;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO dto) {
        try {
            return ResponseEntity.ok(service.save(dto));
        } catch (IllegalArgumentException ex) {
            if ("Duplicate email".equals(ex.getMessage())) {
                return ResponseEntity.status(409).body("Duplicate email");
            }
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserRegistrationDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRegistrationDTO> getUser(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRegistrationDTO dto) {
    try {
        return ResponseEntity.ok(service.update(id, dto));
    } catch (IllegalArgumentException ex) {
        if ("Duplicate email".equals(ex.getMessage())) {
            return ResponseEntity.status(409).body("Duplicate email");
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

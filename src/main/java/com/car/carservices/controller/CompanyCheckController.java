package com.car.carservices.controller;

import com.car.carservices.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/companies/check")
public class CompanyCheckController {

    private final CompanyService service;

    public CompanyCheckController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/manager-email")
    public ResponseEntity<Map<String, Object>> checkManagerEmail(@RequestParam("email") String email) {
        boolean unique = service.isManagerEmailUnique(email);
        return ResponseEntity.ok(Map.of(
                "field", "manager_email",
                "value", email,
                "isUnique", unique,
                "message", unique ? "" : "Duplicate names are not allowed"
        ));
    }

    @GetMapping("/company-name")
    public ResponseEntity<Map<String, Object>> checkCompanyName(@RequestParam("name") String name) {
        boolean unique = service.isCompanyNameUnique(name);
        return ResponseEntity.ok(Map.of(
                "field", "company_name",
                "value", name,
                "isUnique", unique,
                "message", unique ? "" : "Duplicate names are not allowed"
        ));
    }

    @GetMapping("/brand-name")
    public ResponseEntity<Map<String, Object>> checkBrandName(@RequestParam("name") String name) {
        boolean unique = service.isBrandNameUnique(name);
        return ResponseEntity.ok(Map.of(
                "field", "brand_name",
                "value", name,
                "isUnique", unique,
                "message", unique ? "" : "Duplicate names are not allowed"
        ));
    }
}

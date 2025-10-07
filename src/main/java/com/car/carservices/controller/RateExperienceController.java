package com.car.carservices.controller;

import com.car.carservices.dto.RateExperienceDTO;
import com.car.carservices.service.RateExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rate-experiences")
public class RateExperienceController {

    @Autowired
    private RateExperienceService service;

    @PostMapping
    public ResponseEntity<RateExperienceDTO> create(@RequestBody RateExperienceDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateExperienceDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<RateExperienceDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateExperienceDTO> update(@PathVariable Long id, @RequestBody RateExperienceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
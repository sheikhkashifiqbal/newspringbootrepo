package com.car.carservices.controller;

import com.car.carservices.dto.SparePartsRequestDTO;
import com.car.carservices.service.SparePartsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/spareparts-requests")
public class SparePartsRequestController {

    @Autowired
    private SparePartsRequestService service;

    @PostMapping
    public ResponseEntity<SparePartsRequestDTO> create(@RequestBody SparePartsRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartsRequestDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<SparePartsRequestDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartsRequestDTO> update(@PathVariable Long id, @RequestBody SparePartsRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

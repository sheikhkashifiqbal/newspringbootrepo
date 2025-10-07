package com.car.carservices.controller;

import com.car.carservices.dto.CancelReservationDTO;
import com.car.carservices.service.CancelReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cancel-reservations")
public class CancelReservationController {

    @Autowired
    private CancelReservationService service;

    @PostMapping
    public ResponseEntity<CancelReservationDTO> create(@RequestBody CancelReservationDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancelReservationDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CancelReservationDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancelReservationDTO> update(@PathVariable Long id, @RequestBody CancelReservationDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.car.carservices.controller;

import com.car.carservices.dto.ReservationServiceRequestDTO;
import com.car.carservices.service.ReservationServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials = "true")

@RequestMapping("/api/reservations")
public class ReservationServiceRequestController {

    @Autowired
    private ReservationServiceRequestService service;

    @PostMapping
    public ResponseEntity<ReservationServiceRequestDTO> create(@RequestBody ReservationServiceRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationServiceRequestDTO> get(@PathVariable Long id) {
        
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationServiceRequestDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationServiceRequestDTO> update(@PathVariable Long id, @RequestBody ReservationServiceRequestDTO dto) {
        System.out.println("Updateeee");
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
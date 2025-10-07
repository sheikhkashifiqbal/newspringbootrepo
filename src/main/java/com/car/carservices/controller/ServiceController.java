package com.car.carservices.controller;

import com.car.carservices.dto.ServiceDTO;
import com.car.carservices.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @PostMapping
    public ResponseEntity<ServiceDTO> create(@RequestBody ServiceDTO dto) {
        return ResponseEntity.ok(service.createService(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getService(id));
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAll() {
        return ResponseEntity.ok(service.getAllServices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> update(@PathVariable Long id, @RequestBody ServiceDTO dto) {
        return ResponseEntity.ok(service.updateService(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}

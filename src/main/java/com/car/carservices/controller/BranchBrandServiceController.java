package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.service.BranchBrandServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branch-brand-services")
public class BranchBrandServiceController {

    @Autowired
    private BranchBrandServiceService service;

    @PostMapping
    public ResponseEntity<BranchBrandServiceDTO> create(@RequestBody BranchBrandServiceDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchBrandServiceDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<BranchBrandServiceDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchBrandServiceDTO> update(@PathVariable Long id, @RequestBody BranchBrandServiceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
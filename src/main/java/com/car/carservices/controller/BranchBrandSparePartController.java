package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandSparePartDTO;
import com.car.carservices.service.BranchBrandSparePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branch-brand-spareparts")
public class BranchBrandSparePartController {

    @Autowired
    private BranchBrandSparePartService service;

    @PostMapping
    public ResponseEntity<BranchBrandSparePartDTO> create(@RequestBody BranchBrandSparePartDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchBrandSparePartDTO> update(@PathVariable Long id, @RequestBody BranchBrandSparePartDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchBrandSparePartDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BranchBrandSparePartDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
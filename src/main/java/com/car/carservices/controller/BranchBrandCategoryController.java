package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandCategoryDTO;
import com.car.carservices.service.BranchBrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-brand-categories")
public class BranchBrandCategoryController {

    @Autowired
    private BranchBrandCategoryService service;

    @PostMapping
    public ResponseEntity<BranchBrandCategoryDTO> create(@RequestBody BranchBrandCategoryDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchBrandCategoryDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<BranchBrandCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchBrandCategoryDTO> update(@PathVariable Long id, @RequestBody BranchBrandCategoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
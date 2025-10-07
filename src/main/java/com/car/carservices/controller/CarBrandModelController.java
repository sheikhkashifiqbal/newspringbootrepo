package com.car.carservices.controller;

import com.car.carservices.dto.CarBrandModelDTO;
import com.car.carservices.service.CarBrandModelService;
import com.car.carservices.dto.BrandIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/brand-models")
public class CarBrandModelController {

    @Autowired
    private CarBrandModelService service;

    @PostMapping
    public ResponseEntity<CarBrandModelDTO> create(@RequestBody CarBrandModelDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarBrandModelDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarBrandModelDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarBrandModelDTO> update(@PathVariable Long id, @RequestBody CarBrandModelDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @GetMapping("/by-brand/{brandId}")
    public List<CarBrandModelDTO> getModelsByBrand(@PathVariable Long brandId) {
        return service.getModelsByBrandId(brandId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
        public ResponseEntity<List<CarBrandModelDTO>> getModelsByBrandPost(@RequestBody BrandIdRequest req) {
        if (req == null || req.getBrandId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getModelsByBrandId(req.getBrandId()));
    }
}

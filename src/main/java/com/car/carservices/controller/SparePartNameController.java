package com.car.carservices.controller;

import com.car.carservices.dto.SparePartNameDTO;
import com.car.carservices.service.SparePartNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spare-part-names")
public class SparePartNameController {

    @Autowired
    private SparePartNameService service;

    @PostMapping
    public ResponseEntity<SparePartNameDTO> create(@RequestBody SparePartNameDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartNameDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SparePartNameDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartNameDTO> update(@PathVariable Long id,
                                                   @RequestBody SparePartNameDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

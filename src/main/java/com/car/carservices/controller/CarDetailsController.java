package com.car.carservices.controller;

//import com.car.carservices.dto.BranchDTO;
import com.car.carservices.dto.CarDetailsDTO;
import com.car.carservices.service.CarDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

@RequestMapping("/api/cars")
public class CarDetailsController {

    @Autowired
    private CarDetailsService service;

    @GetMapping
    public List<CarDetailsDTO> getAll() { 
        
        return service.getAll(); }
    @PostMapping
    public ResponseEntity<CarDetailsDTO> createCar(@RequestBody CarDetailsDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CarDetailsDTO>> getByUser(@PathVariable Long userId) {
        
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> getCar(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> updateCar(@PathVariable Long id, @RequestBody CarDetailsDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}



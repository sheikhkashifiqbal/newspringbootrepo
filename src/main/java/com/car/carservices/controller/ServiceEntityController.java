package com.car.carservices.controller;

import com.car.carservices.dto.ServiceEntityDTO;
import com.car.carservices.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

@RequestMapping("/api/service-entities")
public class ServiceEntityController {

    @Autowired
    private ServiceEntityService service;

    @PostMapping
    public ServiceEntityDTO create(@RequestBody ServiceEntityDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ServiceEntityDTO update(@PathVariable Long id, @RequestBody ServiceEntityDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public ServiceEntityDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ServiceEntityDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
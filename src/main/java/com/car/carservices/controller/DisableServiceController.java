package com.car.carservices.controller;

import com.car.carservices.dto.BranchBrandServiceDTO;
import com.car.carservices.dto.DisableServiceRequest;
import com.car.carservices.service.DisableService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/disable-service")
public class DisableServiceController {

    private final DisableService service;

    public DisableServiceController(DisableService service) {
        this.service = service;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody DisableServiceRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<BranchBrandServiceDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BranchBrandServiceDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(
            @PathVariable Long id,
            @RequestBody DisableServiceRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}

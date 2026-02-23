package com.car.carservices.controller;

import com.car.carservices.dto.LmpsServiceRequestDTO;
import com.car.carservices.dto.LmpsServiceResponseDTO;
import com.car.carservices.service.LmpsServiceCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/lmps-services")
public class LmpsServiceCrudController {

    private final LmpsServiceCrudService service;

    public LmpsServiceCrudController(LmpsServiceCrudService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LmpsServiceResponseDTO> create(@RequestBody LmpsServiceRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{lmpsServiceId}")
    public ResponseEntity<LmpsServiceResponseDTO> getById(@PathVariable Long lmpsServiceId) {
        return ResponseEntity.ok(service.getById(lmpsServiceId));
    }

    // Optional filters: /api/lmps-services?serviceId=1&cityId=2
    @GetMapping
    public ResponseEntity<List<LmpsServiceResponseDTO>> getAll(
            @RequestParam(value = "serviceId", required = false) Long serviceId,
            @RequestParam(value = "cityId", required = false) Long cityId
    ) {
        return ResponseEntity.ok(service.getAll(serviceId, cityId));
    }

    @PutMapping("/{lmpsServiceId}")
    public ResponseEntity<LmpsServiceResponseDTO> update(
            @PathVariable Long lmpsServiceId,
            @RequestBody LmpsServiceRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(lmpsServiceId, dto));
    }

    @DeleteMapping("/{lmpsServiceId}")
    public ResponseEntity<Void> delete(@PathVariable Long lmpsServiceId) {
        service.delete(lmpsServiceId);
        return ResponseEntity.noContent().build();
    }
}

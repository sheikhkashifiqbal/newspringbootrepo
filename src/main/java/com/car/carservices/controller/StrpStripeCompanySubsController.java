package com.car.carservices.controller;

import com.car.carservices.dto.StrpStripeCompanySubsRequestDTO;
import com.car.carservices.dto.StrpStripeCompanySubsResponseDTO;
import com.car.carservices.service.StrpStripeCompanySubsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/stripe-company-subs")
public class StrpStripeCompanySubsController {

    private final StrpStripeCompanySubsService service;

    public StrpStripeCompanySubsController(StrpStripeCompanySubsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StrpStripeCompanySubsResponseDTO> create(@RequestBody StrpStripeCompanySubsRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrpStripeCompanySubsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StrpStripeCompanySubsResponseDTO>> getAll(
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        if (companyId != null) {
            return ResponseEntity.ok(service.getByCompanyId(companyId));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrpStripeCompanySubsResponseDTO> update(
            @PathVariable Long id,
            @RequestBody StrpStripeCompanySubsRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

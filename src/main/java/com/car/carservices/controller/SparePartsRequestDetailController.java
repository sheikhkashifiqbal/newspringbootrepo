// src/main/java/com/car/carservices/controller/SparePartsRequestDetailController.java
package com.car.carservices.controller;

import com.car.carservices.dto.SparePartsRequestDetailBulkPaymentStatusDTO;
import com.car.carservices.dto.SparePartsRequestDetailDTO;
import com.car.carservices.service.SparePartsRequestDetailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/spare-parts/request-details")
public class SparePartsRequestDetailController {

    private final SparePartsRequestDetailService service;

    public SparePartsRequestDetailController(SparePartsRequestDetailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SparePartsRequestDetailDTO> create(@Valid @RequestBody SparePartsRequestDetailDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartsRequestDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<SparePartsRequestDetailDTO>> list(
            @RequestParam(value = "sparepartsrequest_id", required = false) Long sprId) {
        return ResponseEntity.ok(
                sprId == null ? service.getAll() : service.getByRequestId(sprId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartsRequestDetailDTO> update(
            @PathVariable Long id, @Valid @RequestBody SparePartsRequestDetailDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // ✅ NEW POST API: bulk update payment_status by sparepartsrequest_id
    @PostMapping("/payment-status/by-request")
    public ResponseEntity<Map<String, Object>> updatePaymentStatusByRequest(
            @Valid @RequestBody SparePartsRequestDetailBulkPaymentStatusDTO req
    ) {
        int updated = service.updatePaymentStatusByRequestId(
                req.getSparepartsrequestId(),
                req.getPaymentStatus()
        );

        Map<String, Object> res = new HashMap<>();
        res.put("sparepartsrequest_id", req.getSparepartsrequestId());
        res.put("payment_status", req.getPaymentStatus());
        res.put("updated_count", updated);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Bulk delete all details of one request
    @DeleteMapping("/by-request/{sparepartsrequest_id}")
    public ResponseEntity<Void> deleteByRequest(@PathVariable("sparepartsrequest_id") Long sprId) {
        service.deleteByRequestId(sprId);
        return ResponseEntity.noContent().build();
    }
}
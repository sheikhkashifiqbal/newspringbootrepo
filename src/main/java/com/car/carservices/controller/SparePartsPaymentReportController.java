package com.car.carservices.controller;

import com.car.carservices.dto.SparePartsPaymentReportRequestDTO;
import com.car.carservices.dto.SparePartsPaymentReportResponseDTO;
import com.car.carservices.service.SparePartsPaymentReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/spareparts-payment-services")
public class SparePartsPaymentReportController {

    private final SparePartsPaymentReportService service;

    public SparePartsPaymentReportController(SparePartsPaymentReportService service) {
        this.service = service;
    }

    /**
     * POST /api/spareparts-payment-services/generate
     * Body optional: { "month": 1, "year": 2026 }
     * If missing -> current month/year.
     */
    @PostMapping("/generate")
    public ResponseEntity<List<SparePartsPaymentReportResponseDTO>> generate(
            @RequestBody(required = false) SparePartsPaymentReportRequestDTO req
    ) {
        Integer month = (req == null ? null : req.getMonth());
        Integer year = (req == null ? null : req.getYear());

        // If request not sent -> handled in service using current date
        return ResponseEntity.ok(service.generate(month, year));
    }
}
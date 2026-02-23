package com.car.carservices.controller;

import com.car.carservices.dto.PaymentServicesBranchSummaryDTO;
import com.car.carservices.dto.PaymentServicesGenerateRequestDTO;
import com.car.carservices.service.PaymentServicesGenerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/payment-services")
public class PaymentServicesGenerateController {

    private final PaymentServicesGenerateService service;

    public PaymentServicesGenerateController(PaymentServicesGenerateService service) {
        this.service = service;
    }

    /**
     * POST /api/payment-services/generate
     * Body: { "month":"02", "year":2025 }
     * If body/month/year missing -> uses current month/year.
     */
    @PostMapping("/generate")
    public ResponseEntity<List<PaymentServicesBranchSummaryDTO>> generate(
            @RequestBody(required = false) PaymentServicesGenerateRequestDTO req
    ) {
        String month = (req == null ? null : req.getMonth());
        Integer year = (req == null ? null : req.getYear());
        return ResponseEntity.ok(service.generateAndStore(month, year));
    }
}
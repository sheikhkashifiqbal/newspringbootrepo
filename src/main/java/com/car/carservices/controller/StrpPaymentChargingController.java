package com.car.carservices.controller;

import com.car.carservices.dto.StrpChargePaymentServicesRequestDTO;
import com.car.carservices.dto.StrpPaymentServicesResponseDTO;
import com.car.carservices.service.StrpPaymentChargingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/payment-services")
public class StrpPaymentChargingController {

    private final StrpPaymentChargingService chargingService;

    public StrpPaymentChargingController(StrpPaymentChargingService chargingService) {
        this.chargingService = chargingService;
    }

    /**
     * POST /api/payment-services/charge-unpaid
     * Body: { "month": 1, "year": 2026 }
     * If body/month/year missing -> uses current month/year.
     *
     * Returns only records that were successfully paid (status updated to paid).
     */
    @PostMapping("/charge-unpaid")
    public ResponseEntity<List<StrpPaymentServicesResponseDTO>> chargeUnpaid(
            @RequestBody(required = false) StrpChargePaymentServicesRequestDTO req
    ) {
        return ResponseEntity.ok(chargingService.chargeUnpaidCommissions(req));
    }
}
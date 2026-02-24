package com.car.carservices.service;

import com.car.carservices.dto.StrpChargePaymentServicesRequestDTO;
import com.car.carservices.dto.StrpPaymentServicesResponseDTO;

import java.util.List;

public interface StrpPaymentChargingService {
    List<StrpPaymentServicesResponseDTO> chargeUnpaidCommissions(StrpChargePaymentServicesRequestDTO req);
}
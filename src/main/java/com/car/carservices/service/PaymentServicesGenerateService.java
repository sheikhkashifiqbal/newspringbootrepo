package com.car.carservices.service;

import com.car.carservices.dto.PaymentServicesBranchSummaryDTO;

import java.util.List;

public interface PaymentServicesGenerateService {

    List<PaymentServicesBranchSummaryDTO> generateAndStore(String month, Integer year);
}
package com.car.carservices.service;

import com.car.carservices.dto.SparePartsPaymentReportResponseDTO;

import java.util.List;

public interface SparePartsPaymentReportService {
    List<SparePartsPaymentReportResponseDTO> generate(Integer month, Integer year);
}
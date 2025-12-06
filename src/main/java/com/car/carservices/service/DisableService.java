package com.car.carservices.service;

import com.car.carservices.dto.DisableServiceRequest;
import com.car.carservices.repository.DisableServiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class DisableService {

    private final DisableServiceRepository repository;

    public DisableService(DisableServiceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Map<String, String> disableOrActivateService(DisableServiceRequest request) {

        Long branchId = request.getBranchId();
        Long serviceId = request.getServiceId();

        // ✔ Step 1: Check future bookings
        int activeReservations =
                repository.countActiveReservations(branchId, serviceId);

        Map<String, String> response = new HashMap<>();

        if (activeReservations > 0) {
            response.put("message", "Please cancel all reservations");
            return response;
        }

        // ✔ Step 2: No reservation → Update status
        repository.updateServiceStatus(branchId, serviceId, request.getStatus());

        response.put("message", "Status updated successfully");
        return response;
    }
}

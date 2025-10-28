package com.car.carservices.service;

import com.car.carservices.dto.PRCarLookupResponse;
import com.car.carservices.repository.PRCarLookupReadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PRCarLookupService {

    private final PRCarLookupReadRepository repo;

    public PRCarLookupService(PRCarLookupReadRepository repo) {
        this.repo = repo;
    }

    public Optional<PRCarLookupResponse> findByPlate(String plate) {
        if (plate == null || plate.isBlank()) return Optional.empty();
        return repo.findBrandAndModelByPlate(plate.trim());
    }
}

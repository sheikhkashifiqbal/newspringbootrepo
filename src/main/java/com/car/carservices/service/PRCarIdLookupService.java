package com.car.carservices.service;

import com.car.carservices.repository.PRCarIdLookupReadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PRCarIdLookupService {

    private final PRCarIdLookupReadRepository repo;

    public PRCarIdLookupService(PRCarIdLookupReadRepository repo) {
        this.repo = repo;
    }

    public Optional<Long> findCarId(Long userId, Long brandId, String modelBrand) {
        if (userId == null || brandId == null || modelBrand == null || modelBrand.isBlank()) {
            return Optional.empty();
        }
        return repo.findCarId(userId, brandId, modelBrand.trim());
    }
}

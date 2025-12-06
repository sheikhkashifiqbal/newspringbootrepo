package com.car.carservices.service;

import com.car.carservices.dto.ServicesOfferByBranchProjection;
import com.car.carservices.dto.ServicesOfferByBranchResponse;
import com.car.carservices.repository.ServicesOfferByBranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesOfferByBranchService {

    private final ServicesOfferByBranchRepository servicesOfferByBranchRepository;

    public ServicesOfferByBranchService(ServicesOfferByBranchRepository servicesOfferByBranchRepository) {
        this.servicesOfferByBranchRepository = servicesOfferByBranchRepository;
    }

    public List<ServicesOfferByBranchResponse> getServicesByBranchId(Long branchId) {
        List<ServicesOfferByBranchProjection> rows =
                servicesOfferByBranchRepository.findServicesByBranchId(branchId);

        return rows.stream()
                .map(r -> new ServicesOfferByBranchResponse(
                        r.getServiceId(),
                        r.getServiceName()
                ))
                .collect(Collectors.toList());
    }
}

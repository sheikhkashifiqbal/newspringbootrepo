package com.car.carservices.service.impl;

import com.car.carservices.dto.BrListResponseDTO;
import com.car.carservices.repository.BrListRepository;
import com.car.carservices.service.BrListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrListServiceImpl implements BrListService {

    private final BrListRepository repo;

    public BrListServiceImpl(BrListRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<BrListResponseDTO> getBrandsList(Long branchId) {
        return repo.findBrandsByBranchId(branchId).stream()
                .map(p -> new BrListResponseDTO(
                        p.getBrand_id(),
                        p.getBrand_name(),
                        p.getStatus()
                ))
                .collect(Collectors.toList());
    }
}

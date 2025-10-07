package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchSearchRequestDTO;
import com.car.carservices.dto.BranchSearchResponseDTO;
import com.car.carservices.repository.BranchRepositoryResponse;
import com.car.carservices.service.BranchSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchSearchServiceImpl implements BranchSearchService {

    @Autowired
    private BranchRepositoryResponse branchRepositoryResponse;

    @Override
    public List<BranchSearchResponseDTO> searchBranches(BranchSearchRequestDTO request) {
        return branchRepositoryResponse.searchBranches(
            request.getBrandName(),
            request.getServiceName(),
           // request.getWorkdays(),
            request.getLocation()
        );
    }
}

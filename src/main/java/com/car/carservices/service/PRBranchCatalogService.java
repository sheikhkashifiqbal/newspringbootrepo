package com.car.carservices.service;

import com.car.carservices.dto.*;
import com.car.carservices.repository.PRBranchCatalogReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRBranchCatalogService {

    private final PRBranchCatalogReadRepository repo;

    public PRBranchCatalogService(PRBranchCatalogReadRepository repo) {
        this.repo = repo;
    }

    public PRBranchCatalogResponse getCatalogForBranch(Long branchId) {
        List<PRBrandDTO> brands = repo.findBrandsByBranch(branchId);
        List<PRServiceDTO> services = repo.findServicesByBranch(branchId);
        List<PRModelDTO> models = repo.findModelsByBranch(branchId);
        return new PRBranchCatalogResponse(brands, services, models);
    }
    public List<PRBrandDTO> getBrandsForBranch(Long branchId) {
    return repo.findBrandsByBranch(branchId);
}

    public List<PRServiceDTO> getServicesForBranchAndBrand(Long branchId, Long brandId) {
        return repo.findServicesByBranchAndBrand(branchId, brandId);
    }

}

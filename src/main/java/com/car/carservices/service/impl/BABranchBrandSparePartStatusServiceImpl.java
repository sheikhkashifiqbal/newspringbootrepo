package com.car.carservices.service.impl;

import com.car.carservices.dto.BABranchBrandSparePartStatusRequestDTO;
import com.car.carservices.dto.BABranchBrandSparePartStatusResponseDTO;
import com.car.carservices.repository.BABranchBrandSparePartDao;
import com.car.carservices.service.BABranchBrandSparePartStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class BABranchBrandSparePartStatusServiceImpl implements BABranchBrandSparePartStatusService {

    private final BABranchBrandSparePartDao dao;

    public BABranchBrandSparePartStatusServiceImpl(BABranchBrandSparePartDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public BABranchBrandSparePartStatusResponseDTO updateStatus(BABranchBrandSparePartStatusRequestDTO req) {

        if (req == null) {
            throw new IllegalArgumentException("Request body is required");
        }
        if (req.getBranchId() == null) {
            throw new IllegalArgumentException("branch_id is required");
        }
        if (req.getBrandId() == null) {
            throw new IllegalArgumentException("brand_id is required");
        }
        if (req.getStatus() == null || req.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("status is required");
        }

        String status = req.getStatus().trim().toLowerCase(Locale.ROOT);
        if (!status.equals("active") && !status.equals("inactive")) {
            throw new IllegalArgumentException("status must be 'active' or 'inactive'");
        }

        // Optional: check if records exist; if not, return updated_count=0
        if (!dao.existsForBranchAndBrand(req.getBranchId(), req.getBrandId())) {
            return new BABranchBrandSparePartStatusResponseDTO(
                    req.getBranchId(),
                    req.getBrandId(),
                    status,
                    0
            );
        }

        int updated = dao.updateStatusByBranchAndBrand(req.getBranchId(), req.getBrandId(), status);

        return new BABranchBrandSparePartStatusResponseDTO(
                req.getBranchId(),
                req.getBrandId(),
                status,
                updated
        );
    }
}
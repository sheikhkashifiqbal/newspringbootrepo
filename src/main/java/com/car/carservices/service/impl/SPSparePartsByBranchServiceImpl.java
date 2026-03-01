package com.car.carservices.service.impl;

import com.car.carservices.dto.SPAvailableSparePartDTO;
import com.car.carservices.dto.SPBrandSparePartsResponseDTO;
import com.car.carservices.repository.SPBranchBrandSparePartReportDao;
import com.car.carservices.service.SPSparePartsByBranchService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SPSparePartsByBranchServiceImpl implements SPSparePartsByBranchService {

    private final SPBranchBrandSparePartReportDao reportDao;

    public SPSparePartsByBranchServiceImpl(SPBranchBrandSparePartReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public List<SPBrandSparePartsResponseDTO> getSparePartsByBranch(Long branchId) {

        if (branchId == null) {
            throw new IllegalArgumentException("branch_id is required");
        }

        List<SPBranchBrandSparePartReportDao.Row> rows =
                reportDao.findRowsByBranchId(branchId);

        // Group by brand_id
        Map<Long, SPBrandSparePartsResponseDTO> brandMap = new LinkedHashMap<>();

        for (var r : rows) {
            SPBrandSparePartsResponseDTO brandDto = brandMap.computeIfAbsent(
                    r.brandId(),
                    k -> new SPBrandSparePartsResponseDTO(r.brandId(), r.brandName(), r.status())
            );

            brandDto.getAvailableSpaeparts().add(new SPAvailableSparePartDTO(
                    r.id(),
                    r.sparepartsId(),
                    r.sparepartsType(),
                    r.state()
            ));
        }

        return new ArrayList<>(brandMap.values());
    }
}
package com.car.carservices.service.impl;

import com.car.carservices.dto.*;
import com.car.carservices.entity.CarDetails;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchSparePartSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchSparePartSearchServiceImpl implements BranchSparePartSearchService {

    @Autowired private CarDetailsRepository carRepo;
    @Autowired private BranchBrandSparePartsRepository bbspRepo;

    @Override
    public List<BranchSparePartResultDTO> search(BranchSparePartSearchDTO dto) {
        // 1) find car brand via VIN
        CarDetails car = carRepo.findByVinNumber(dto.getVinNumber())
                .orElseThrow(() -> new IllegalArgumentException("VIN not found"));
        Long brandId = car.getBrand().getBrandId();

        // 2) query branches
        List<Object[]> rows = bbspRepo.searchBranchesNative(
                brandId,
                dto.getCity(),
                dto.getSparePartType(),
                dto.getCondition(),
                dto.getSparePartNames());

        // 3) convert Object[] → DTO
        return rows.stream().map(r -> {
            BranchSparePartResultDTO res = new BranchSparePartResultDTO();
            res.setBranchId(((Number) r[0]).longValue());
            res.setBranchName((String) r[1]);
            res.setBranchAddress((String) r[2]);
            res.setBrandName((String) r[3]);
            res.setLocation((String) r[4]);
            res.setTotalMatchedParts(((Number) r[5]).intValue());
            return res;
        }).collect(Collectors.toList());
    }
}

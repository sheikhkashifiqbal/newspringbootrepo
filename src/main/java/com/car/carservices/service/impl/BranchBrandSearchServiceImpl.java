// com/car/carservices/service/impl/BranchBrandSearchServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.*;
import com.car.carservices.entity.*;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchBrandSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchBrandSearchServiceImpl implements BranchBrandSearchService {

    @Autowired
    private BranchBrandRepository branchRepository;

    @Autowired
    private BranchBrandServiceRepository bbsRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandGroupSearchResultDTO> searchBranches(BranchSearchDTO searchDTO) {

        // Fetch branches that match the incoming brand & services
        List<Branch> branches =
            branchRepository.findBranchesByBrandAndServices(searchDTO.getBrandId(), searchDTO.getServiceIds());

        // Group branches by brand:
        // - If request has a brandId, use it as the single group key
        // - Otherwise, infer the first brand linked to each branch via BBS
        Map<Long, List<Branch>> grouped = new LinkedHashMap<>();
        for (Branch branch : branches) {
            Long key = (searchDTO.getBrandId() != null)
                ? searchDTO.getBrandId()
                : bbsRepository.findByBranch_BranchId(branch.getBranchId()).stream()
                    .map(bbs -> bbs.getBrand().getBrandId())
                    .findFirst()
                    .orElse(0L); // unknown brand bucket
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(branch);
        }

        List<BrandGroupSearchResultDTO> result = new ArrayList<>();

        for (Map.Entry<Long, List<Branch>> entry : grouped.entrySet()) {
            Long brandId = entry.getKey();
            String brandName = brandRepository.findById(brandId)
                    .map(Brand::getBrandName)
                    .orElse("Unknown Brand");

            // Build branch + services list for this brand
            List<BranchWithServicesDTO> branchDTOs = entry.getValue().stream().map(branch -> {
                BranchWithServicesDTO dto = new BranchWithServicesDTO();
                dto.setBranchId(branch.getBranchId());
                dto.setBranchName(branch.getBranchName());
                dto.setLocation(branch.getLocation());

                List<String> services = bbsRepository.findByBranch_BranchId(branch.getBranchId()).stream()
                        .filter(bbs -> searchDTO.getServiceIds().contains(bbs.getService().getServiceId()))
                        .map(bbs -> bbs.getService().getServiceName())
                        .distinct()
                        .collect(Collectors.toList());

                dto.setServices(services);
                return dto;
            }).collect(Collectors.toList());

            BrandGroupSearchResultDTO group = new BrandGroupSearchResultDTO();
            group.setBrandId(brandId);
            group.setBrandName(brandName);
            group.setBranches(branchDTOs);

            result.add(group);
        }

        return result;
    }
}

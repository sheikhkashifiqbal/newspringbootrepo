package com.car.carservices.service.impl;

import com.car.carservices.dto.*;
import com.car.carservices.repository.SparePartsBranchRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SparePartsBranchService {

    private final SparePartsBranchRepo repo;

    public SparePartsBranchService(SparePartsBranchRepo repo) {
        this.repo = repo;
    }

    public List<SparePartsBranchResponse> getByBranch(SparePartsBranchRequest req) {

        List<Map<String, Object>> rows = repo.findByBranch(req.getBranch_id());
        List<SparePartsBranchResponse> response = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            SparePartsBranchResponse dto = new SparePartsBranchResponse();

            Long spareReqId = ((Number) row.get("sparepartsrequest_id")).longValue();

            dto.setSparepartsrequest_id(spareReqId);
            dto.setDate(row.get("date").toString());
            dto.setBranch_name((String) row.get("branch_name"));
            dto.setAddress((String) row.get("address"));
            dto.setCity((String) row.get("city"));
            dto.setVIN((String) row.get("vin"));
            dto.setSpareparts_type((String) row.get("spareparts_type"));
            dto.setState((String) row.get("state"));
            dto.setRequest_status((String) row.get("request_status"));
            dto.setManager_mobile((String) row.get("manager_mobile"));

            // NEW FIELDS
            dto.setBrand_id(((Number) row.get("brand_id")).longValue());
            dto.setBrand_name((String) row.get("brand_name"));

            // Fetch spare part list
            List<Map<String, Object>> parts = repo.findSparePartsByRequest(spareReqId);
            List<SparePartsItemResponse> partDTOs = new ArrayList<>();

            for (Map<String, Object> p : parts) {
                SparePartsItemResponse item = new SparePartsItemResponse();
                item.setId(((Number) p.get("id")).longValue());
                item.setSparepartsrequest_id(spareReqId);
                item.setSpare_part((String) p.get("spare_part"));
                item.setClass_type((String) p.get("class_type"));
                item.setQty(((Number) p.get("qty")).intValue());
                item.setPrice(((Number) p.get("price")).doubleValue());
                partDTOs.add(item);
            }

            dto.setSpare_part(partDTOs);
            response.add(dto);
        }

        return response;
    }
}

// src/main/java/com/car/carservices/service/SparePartOfferService.java
package com.car.carservices.service;

import com.car.carservices.dto.SparePartOfferResponse;
import com.car.carservices.dto.SparePartOfferBranchResponse;

import java.util.List;

public interface SparePartOfferService {
    List<SparePartOfferResponse> byUserId(Long userId);
    List<SparePartOfferResponse> byUserAndBranch(Long userId, Long branchId); // existing NEW
    List<SparePartOfferBranchResponse> byBranch(Long branchId);               // NEW
}

// com/car/carservices/dto/BranchSearchResultDTO.java
package com.car.carservices.dto;

import java.util.List;

public record BranchSearchResultDTO(
    Long branchId,
    String branchName,
    String location,
    Double companyRating,      // average stars (rate_experience)
    Double distanceKm,         // null if coords missing
    String companyLogoUrl,     // branch.logo_img (or company if you have it)
    List<String> availableTimeSlots, // capacity-aware, 30-min, inclusive "to"
    Double price               // nullable; read from branch_brand_service.price if present
) {}

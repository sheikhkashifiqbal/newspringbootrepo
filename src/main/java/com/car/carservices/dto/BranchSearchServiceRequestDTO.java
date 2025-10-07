// com/car/carservices/dto/BranchSearchServiceRequestDTO.java
package com.car.carservices.dto;

import java.time.LocalDate;



public record BranchSearchServiceRequestDTO(
    String carBrand,          // nullable
    String carModel,          // nullable
    String serviceEntity,     // nullable
    LocalDate date,           // supports ISO "yyyy-MM-dd"
    String dateText,          // also supports "10 Sep", "20 Dec"
    String location,          // filter by city (nullable)
    
    
    Double currentLat,        // nullable (for distance)
    Double currentLon,        // nullable (for distance)
    
    
    SortBy sortBy             // nullable
) {
    public enum SortBy {
        DISTANCE_CLOSEST,
        DISTANCE_FARTHEST,
        RATING_HIGH_TO_LOW,
        RATING_LOW_TO_HIGH,
        PRICE_LOW_TO_HIGH,
        PRICE_HIGH_TO_LOW
    }
}
